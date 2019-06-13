package star.vo;


public class FullcolumnObjList implements Comparable<FullcolumnObjList>{

	private String url;
	
	private String img;
	
	private Integer orderBy;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public Integer getOrderby() {
		return orderBy;
	}

	public void setOrderby(Integer orderBy) {
		this.orderBy = orderBy;
	}
	@Override
	public int compareTo(FullcolumnObjList o) {
		if(o.getOrderby()==null||this.getOrderby()==null){
			return 0;
		}
		return o.getOrderby().compareTo(this.getOrderby());
	}

	@Override
	public String toString() {
		return "FullcolumnObjList [url=" + url + ", img=" + img + ", orderBy=" + orderBy + "]";
	}
}
