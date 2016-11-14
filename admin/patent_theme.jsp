<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<html>
<head>
<script type="text/javascript" src="http://mbostock.github.com/d3/d3.v2.js?2.9.1"></script>
<style type="text/css">
.link { stroke: green; stroke-linejoin:bevel;}

.link_error{
    stroke:red;
    stroke-linejoin:bevel;
}

.nodetext {

    font: 12px sans-serif;
    -webkit-user-select:none;
    -moze-user-select:none;
    stroke-linejoin:bevel;
    
}

#container{
    width:100%;
    height:800px;
    border:1px solid gray;
    border-radius:5px;
    position:relative;
    margin:20px;
}
</style>
</head>
<body>
    <div id='container'></div>
<script type="text/javascript">

function Topology(ele){
    typeof(ele)=='string' && (ele=document.getElementById(ele));
    var w=ele.clientWidth,
        h=ele.clientHeight,
        self=this;
    this.force = d3.layout.force().gravity(.05).distance(200).charge(-800).size([w, h]);
    this.nodes=this.force.nodes();
    this.links=this.force.links();
    this.clickFn=function(){};
    this.vis = d3.select(ele).append("svg:svg")
                 .attr("width", w).attr("height", h).attr("pointer-events", "all");

    this.force.on("tick", function(x) {
      self.vis.selectAll("g.node")
          .attr("transform", function(d) { return "translate(" + d.x + "," + d.y + ")"; });

      self.vis.selectAll("line.link")
          .attr("x1", function(d) { return d.source.x; })
          .attr("y1", function(d) { return d.source.y; })
          .attr("x2", function(d) { return d.target.x; })
          .attr("y2", function(d) { return d.target.y; });
    });
}


Topology.prototype.doZoom=function(){
    d3.select(this).select('g').attr("transform","translate(" + d3.event.translate + ")"+ " scale(" + d3.event.scale + ")");

}


//增加节点
Topology.prototype.addNode=function(node){
    this.nodes.push(node);
}

Topology.prototype.addNodes=function(nodes){
    if (Object.prototype.toString.call(nodes)=='[object Array]' ){
        var self=this;
        nodes.forEach(function(node){
            self.addNode(node);
        });

    }
}

//增加连线
Topology.prototype.addLink=function(source,target){
    this.links.push({source:this.findNode(source),target:this.findNode(target)});
}

//增加多个连线
Topology.prototype.addLinks=function(links){
    if (Object.prototype.toString.call(links)=='[object Array]' ){
        var self=this;
        links.forEach(function(link){
            self.addLink(link['source'],link['target']);
        });
    }
}


//删除节点
Topology.prototype.removeNode=function(id){
    var i=0,
        n=this.findNode(id),
        links=this.links;
    while ( i < links.length){
        links[i]['source']==n || links[i]['target'] ==n ? links.splice(i,1) : ++i;
    }
    this.nodes.splice(this.findNodeIndex(id),1);
}

//删除节点下的子节点，同时清除link信息
Topology.prototype.removeChildNodes=function(id){
    var node=this.findNode(id),
        nodes=this.nodes;
        links=this.links,
        self=this;

    var linksToDelete=[],
        childNodes=[];
    
    links.forEach(function(link,index){
        link['source']==node 
            && linksToDelete.push(index) 
            && childNodes.push(link['target']);
    });

    linksToDelete.reverse().forEach(function(index){
        links.splice(index,1);
    });

    var remove=function(node){
        var length=links.length;
        for(var i=length-1;i>=0;i--){
            if (links[i]['source'] == node ){
               var target=links[i]['target'];
               links.splice(i,1);
               nodes.splice(self.findNodeIndex(node.id),1);
               remove(target);
               
            }
        }
    }

    childNodes.forEach(function(node){
        remove(node);
    });

    //清除没有连线的节点
    for(var i=nodes.length-1;i>=0;i--){
        var haveFoundNode=false;
        for(var j=0,l=links.length;j<l;j++){
            ( links[j]['source']==nodes[i] || links[j]['target']==nodes[i] ) && (haveFoundNode=true) 
        }
        !haveFoundNode && nodes.splice(i,1);
    }
}



//查找节点
Topology.prototype.findNode=function(id){
    var nodes=this.nodes;
    for (var i in nodes){
        if (nodes[i]['id']==id ) return nodes[i];
    }
    return null;
}


//查找节点所在索引号
Topology.prototype.findNodeIndex=function(id){
    var nodes=this.nodes;
    for (var i in nodes){
        if (nodes[i]['id']==id ) return i;
    }
    return -1;
}

//节点点击事件
Topology.prototype.setNodeClickFn=function(callback){
    this.clickFn=callback;
}

//更新拓扑图状态信息
Topology.prototype.update=function(){
  var link = this.vis.selectAll("line.link")
      .data(this.links, function(d) { return d.source.id + "-" + d.target.id; })
      .attr("class", function(d){
            return d['source']['status'] && d['target']['status'] ? 'link' :'link link_error';
      });

  link.enter().insert("svg:line", "g.node")
      .attr("class", function(d){
         return d['source']['status'] && d['target']['status'] ? 'link' :'link link_error';
      });

  link.exit().remove();

  var node = this.vis.selectAll("g.node")
      .data(this.nodes, function(d) { return d.id;});

  var nodeEnter = node.enter().append("svg:g")
      .attr("class", "node")
      .call(this.force.drag);

  //增加图片，可以根据需要来修改
  var self=this;
  nodeEnter.append("svg:image")
      .attr("class", "circle")
      .attr("xlink:href", function(d){
         //根据类型来使用图片
         return d.expand ? "0.gif" : "1.gif";
      })
      .attr("x", "-15px")
      .attr("y", "-15px")
      .attr("width", "32px")
      .attr("height", "32px")
      .on('click',function(d){ d.expand && self.clickFn(d);})

  nodeEnter.append("svg:text")
      .attr("class", "nodetext")
      .attr("dx", 15)
      .attr("dy", -35)
      .text(function(d) { return d.id });

  
  node.exit().remove();

  this.force.start();
}




var topology=new Topology('container');

var nodes = [
		{id:'专利主题', type:'router', status:1},
		{id:'topic0', type:'switch', status:1, expand:true},
		{id:'topic1', type:'switch', status:1, expand:true},
		{id:'topic2', type:'switch', status:1, expand:true},
		{id:'topic3', type:'switch', status:1, expand:true},
		{id:'topic4', type:'switch', status:1, expand:true},
		{id:'topic5', type:'switch', status:1, expand:true},
		{id:'topic6', type:'switch', status:1, expand:true},
		{id:'topic7', type:'switch', status:1, expand:true},
		{id:'topic8', type:'switch', status:1, expand:true},
		{id:'topic9', type:'switch', status:1, expand:true}
		
	];
var topic0Nodes = [
		{id:'模块', type:'switch', status:1},
		{id:'智能', type:'switch', status:1},
		{id:'系统', type:'switch', status:1},
		{id:'单元', type:'switch', status:1},
		{id:'采集', type:'switch', status:1},
		{id:'机器人', type:'switch', status:1},
		{id:'控制', type:'switch', status:1},
		{id:'人工智能', type:'switch', status:1},
		{id:'处理', type:'switch', status:1},
		{id:'设备', type:'switch', status:1}
	];
var topic1Nodes = [
		{id:'操作', type:'switch', status:1},
		{id:'图像', type:'switch', status:1},
		{id:'特征', type:'switch', status:1},
		{id:'识别', type:'switch', status:1},
		{id:'方法', type:'switch', status:1},
		{id:'预测', type:'switch', status:1},
		{id:'信号', type:'switch', status:1},
		{id:'处理', type:'switch', status:1},
		{id:'分类', type:'switch', status:1},
		{id:'参数', type:'switch', status:1}
	];
var topic2Nodes = [
		{id:'人工智能', type:'switch', status:1},
		{id:'设计', type:'switch', status:1},
		{id:'产品', type:'switch', status:1},
		{id:'元件', type:'switch', status:1},
		{id:'材料', type:'switch', status:1},
		{id:'工艺', type:'switch', status:1},
		{id:'电极', type:'switch', status:1},
		{id:'架构', type:'switch', status:1},
		{id:'采用', type:'switch', status:1},
		{id:'逻辑服务器', type:'switch', status:1}
	];
var topic3Nodes = [
		{id:'控制', type:'switch', status:1},
		{id:'装置', type:'switch', status:1},
		{id:'传感器', type:'switch', status:1},
		{id:'电路', type:'switch', status:1},
		{id:'控制器', type:'switch', status:1},
		{id:'信号源', type:'switch', status:1},
		{id:'连接装置', type:'switch', status:1},
		{id:'人工智能', type:'switch', status:1},
		{id:'限流功率', type:'switch', status:1},
		{id:'包括', type:'switch', status:1}
	];
var topic4Nodes = [
		{id:'范式', type:'switch', status:1},
		{id:'体系', type:'switch', status:1},
		{id:'系统', type:'switch', status:1},
		{id:'建立', type:'switch', status:1},
		{id:'基础', type:'switch', status:1},
		{id:'方程', type:'switch', status:1},
		{id:'模型', type:'switch', status:1},
		{id:'网络', type:'switch', status:1},
		{id:'计算', type:'switch', status:1},
		{id:'互联网', type:'switch', status:1}
	];
var topic5Nodes = [
		{id:'神经网络', type:'switch', status:1},
		{id:'运行', type:'switch', status:1},
		{id:'算法', type:'switch', status:1},
		{id:'步骤', type:'switch', status:1},
		{id:'参数', type:'switch', status:1},
		{id:'预测', type:'switch', status:1},
		{id:'优化', type:'switch', status:1},
		{id:'数据', type:'switch', status:1},
		{id:'模型', type:'switch', status:1},
		{id:'方法', type:'switch', status:1}
	];
var topic6Nodes = [
		{id:'语音', type:'switch', status:1},
		{id:'手机', type:'switch', status:1},
		{id:'识别', type:'switch', status:1},
		{id:'软件', type:'switch', status:1},
		{id:'计算机', type:'switch', status:1},
		{id:'功能', type:'switch', status:1},
		{id:'方案', type:'switch', status:1},
		{id:'技术', type:'switch', status:1},
		{id:'发电机组', type:'switch', status:1},
		{id:'机器', type:'switch', status:1}
	];
var topic7Nodes = [
		{id:'行为', type:'switch', status:1},
		{id:'搜索', type:'switch', status:1},
		{id:'提供', type:'switch', status:1},
		{id:'包括', type:'switch', status:1},
		{id:'信息', type:'switch', status:1},
		{id:'用户', type:'switch', status:1},
		{id:'方法', type:'switch', status:1},
		{id:'人工智能', type:'switch', status:1},
		{id:'输入', type:'switch', status:1},
		{id:'生成', type:'switch', status:1}
	];
var topic8Nodes = [
		{id:'定位', type:'switch', status:1},
		{id:'车辆', type:'switch', status:1},
		{id:'显示', type:'switch', status:1},
		{id:'系统', type:'switch', status:1},
		{id:'单元', type:'switch', status:1},
		{id:'安全', type:'switch', status:1},
		{id:'装置', type:'switch', status:1},
		{id:'分类', type:'switch', status:1},
		{id:'使用', type:'switch', status:1},
		{id:'人工智能', type:'switch', status:1}
	];
var topic9Nodes = [
		{id:'模块', type:'switch', status:1},
		{id:'数据', type:'switch', status:1},
		{id:'智能', type:'switch', status:1},
		{id:'监控', type:'switch', status:1},
		{id:'采集', type:'switch', status:1},
		{id:'系统', type:'switch', status:1},
		{id:'分析', type:'switch', status:1},
		{id:'处理', type:'switch', status:1},
		{id:'智能', type:'switch', status:1},
		{id:'包括', type:'switch', status:1}
	];
var topic0Links = [
		{source:'topic0', target:'模块'},
		{source:'topic0', target:'智能'},
		{source:'topic0', target:'系统'},
		{source:'topic0', target:'单元'},
		{source:'topic0', target:'采集'},
		{source:'topic0', target:'机器人'},
		{source:'topic0', target:'控制'},
		{source:'topic0', target:'人工智能'},
		{source:'topic0', target:'处理'},
		{source:'topic0', target:'设备'}
	];
var topic1Links = [
		{source:'topic1', target:'操作'},
		{source:'topic1', target:'图像'},
		{source:'topic1', target:'特征'},
		{source:'topic1', target:'识别'},
		{source:'topic1', target:'方法'},
		{source:'topic1', target:'预测'},
		{source:'topic1', target:'信号'},
		{source:'topic1', target:'处理'},
		{source:'topic1', target:'分类'},
		{source:'topic1', target:'参数'}
	];
var topic2Links = [
		{source:'topic2', target:'人工智能'},
		{source:'topic2', target:'设计'},
		{source:'topic2', target:'产品'},
		{source:'topic2', target:'元件'},
		{source:'topic2', target:'材料'},
		{source:'topic2', target:'工艺'},
		{source:'topic2', target:'电极'},
		{source:'topic2', target:'架构'},
		{source:'topic2', target:'采用'},
		{source:'topic2', target:'逻辑服务器'}
	];
var topic3Links = [
		{source:'topic3', target:'控制'},
		{source:'topic3', target:'装置'},
		{source:'topic3', target:'传感器'},
		{source:'topic3', target:'电路'},
		{source:'topic3', target:'控制器'},
		{source:'topic3', target:'信号源'},
		{source:'topic3', target:'连接装置'},
		{source:'topic3', target:'人工智能'},
		{source:'topic3', target:'限流功率'},
		{source:'topic3', target:'包括'}
	];
var topic4Links = [
		{source:'topic4', target:'范式'},
		{source:'topic4', target:'体系'},
		{source:'topic4', target:'系统'},
		{source:'topic4', target:'建立'},
		{source:'topic4', target:'基础'},
		{source:'topic4', target:'方程'},
		{source:'topic4', target:'模型'},
		{source:'topic4', target:'网络'},
		{source:'topic4', target:'计算'},
		{source:'topic4', target:'互联网'}
	];
var topic5Links = [
		{source:'topic5', target:'神经网络'},
		{source:'topic5', target:'运行'},
		{source:'topic5', target:'算法'},
		{source:'topic5', target:'步骤'},
		{source:'topic5', target:'参数'},
		{source:'topic5', target:'预测'},
		{source:'topic5', target:'优化'},
		{source:'topic5', target:'数据'},
		{source:'topic5', target:'模型'},
		{source:'topic5', target:'方法'}
	];
var topic6Links = [
		{source:'topic6', target:'语音'},
		{source:'topic6', target:'手机'},
		{source:'topic6', target:'软件'},
		{source:'topic6', target:'识别'},
		{source:'topic6', target:'计算机'},
		{source:'topic6', target:'功能'},
		{source:'topic6', target:'方案'},
		{source:'topic6', target:'技术'},
		{source:'topic6', target:'发电机组'},
		{source:'topic6', target:'机器'}
	];
var topic7Links = [
		{source:'topic7', target:'行为'},
		{source:'topic7', target:'搜索'},
		{source:'topic7', target:'提供'},
		{source:'topic7', target:'包括'},
		{source:'topic7', target:'信息'},
		{source:'topic7', target:'用户'},
		{source:'topic7', target:'方法'},
		{source:'topic7', target:'人工智能'},
		{source:'topic7', target:'输入'},
		{source:'topic7', target:'生成'}
	];
var topic8Links = [
		{source:'topic8', target:'定位'},
		{source:'topic8', target:'车辆'},
		{source:'topic8', target:'显示'},
		{source:'topic8', target:'系统'},
		{source:'topic8', target:'单元'},
		{source:'topic8', target:'安全'},
		{source:'topic8', target:'装置'},
		{source:'topic8', target:'分类'},
		{source:'topic8', target:'使用'},
		{source:'topic8', target:'人工智能'}
	];
var topic9Links = [
		{source:'topic9', target:'模块'},
		{source:'topic9', target:'数据'},
		{source:'topic9', target:'智能'},
		{source:'topic9', target:'监控'},
		{source:'topic9', target:'采集'},
		{source:'topic9', target:'系统'},
		{source:'topic9', target:'分析'},
		{source:'topic9', target:'处理'},
		{source:'topic9', target:'智能'},
		{source:'topic9', target:'包括'}
	];
var links = [
		{source:'专利主题', target:'topic0'},
		{source:'专利主题', target:'topic1'},
		{source:'专利主题', target:'topic2'},
		{source:'专利主题', target:'topic3'},
		{source:'专利主题', target:'topic4'},
		{source:'专利主题', target:'topic5'},
		{source:'专利主题', target:'topic6'},
		{source:'专利主题', target:'topic7'},
		{source:'专利主题', target:'topic8'},
		{source:'专利主题', target:'topic9'}
		
	];


topology.addNodes(nodes);
topology.addLinks(links);
//可展开节点的点击事件
topology.setNodeClickFn(function(node){
    if(!node['_expanded']){
        expandNode(node.id);
        node['_expanded']=true;
    }else{
        collapseNode(node.id);
        node['_expanded']=false;
    }
});
topology.update();


function expandNode(id){
  topology.addNodes(topic0Nodes);
  topology.addLinks(topic0Links);
  topology.update();
  
  topology.addNodes(topic1Nodes);
  topology.addLinks(topic1Links);
  topology.update();
  
  topology.addNodes(topic2Nodes);
  topology.addLinks(topic2Links);
  topology.update();
  
  topology.addNodes(topic3Nodes);
  topology.addLinks(topic3Links);
  topology.update();
  
  topology.addNodes(topic4Nodes);
  topology.addLinks(topic4Links);
  topology.update();
  
  topology.addNodes(topic5Nodes);
  topology.addLinks(topic5Links);
  topology.update();
  
  topology.addNodes(topic6Nodes);
  topology.addLinks(topic6Links);
  topology.update();
  
  topology.addNodes(topic7Nodes);
  topology.addLinks(topic7Links);
  topology.update();
  
  topology.addNodes(topic8Nodes);
  topology.addLinks(topic8Links);
  topology.update();
  
  topology.addNodes(topic9Nodes);
  topology.addLinks(topic9Links);
  topology.update();
}

function collapseNode(id){
    topology.removeChildNodes(id);
    topology.update();
}
</script>

</body>
</html>