package com.patent.util;

import java.util.List;

/** 供s:doubleselect标签使用的级联节点 */
public class DoubleSelectNode {
	String name;//供显示的内容
	String value;//节点的值
	List<DoubleSelectNode> subNodes;//级联子节点集
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public List<DoubleSelectNode> getSubNodes() {
		return subNodes;
	}
	public void setSubNodes(List<DoubleSelectNode> subNodes) {
		this.subNodes = subNodes;
	}
}
