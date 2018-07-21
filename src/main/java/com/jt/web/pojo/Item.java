package com.jt.web.pojo;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.jt.common.po.BasePojo;
//通过通用Mapper实现表数据的操作
public class Item extends BasePojo{
	
	//标识主键信息  表示主键自增
	private Long id;				//商品ID
	private String title;			//商品标题
	private String sellPoint;		//卖点信息
	private Long price;				//价格信息  int > long >dubbo
	private Integer num;			//商品的数量
	private String barcode;			//二维码信息
	private String image;			//图片信息
	private Long cid;				//商品分类信息
	private Integer status;
	
	//为了获取图片的第一张大图需要通过数组进行获取
	public String[] getImages(){
		return image.split(","); 
	}
	
	//商品的状态   可选值：1正常，2下架，3删除
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getSellPoint() {
		return sellPoint;
	}
	public void setSellPoint(String sellPoint) {
		this.sellPoint = sellPoint;
	}
	public Long getPrice() {
		return price;
	}
	public void setPrice(Long price) {
		this.price = price;
	}
	public Integer getNum() {
		return num;
	}
	public void setNum(Integer num) {
		this.num = num;
	}
	public String getBarcode() {
		return barcode;
	}
	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public Long getCid() {
		return cid;
	}
	public void setCid(Long cid) {
		this.cid = cid;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}

}
