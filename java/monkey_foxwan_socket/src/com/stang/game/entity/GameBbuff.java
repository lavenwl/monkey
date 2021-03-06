package com.stang.game.entity;

public class GameBbuff implements Cloneable{
	@Override
	protected Object clone() throws CloneNotSupportedException {
		// 实现clone方法
		return super.clone();
	}
	//缓存中确定是否更新，插入，删除字段。默认：0；更新：1；插入：2；删除：3.
	private int isUpdate;
	
	private int id;
	private String name;
	private String artdesc;
	private int cishu;
	private int time;
	private int shanghai;
	private int type;
	private int flag;
	
	public int getFlag(){
		return flag;
	}
	public void setFlag(int flag){
		this.flag = flag;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public String getArtdesc() {
		return artdesc;
	}
	public void setArtdesc(String artdesc) {
		this.artdesc = artdesc;
	}
	public int getCishu() {
		return cishu;
	}
	public void setCishu(int cishu) {
		this.cishu = cishu;
	}
	public int getTime() {
		return time;
	}
	public void setTime(int time) {
		this.time = time;
	}
	public int getShanghai() {
		return shanghai;
	}
	public void setShanghai(int shanghai) {
		this.shanghai = shanghai;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getIsUpdate() {
		return isUpdate;
	}
	public void setIsUpdate(int isUpdate) {
		this.isUpdate = isUpdate;
	}
	
	
}
