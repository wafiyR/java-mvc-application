package product.bk.com;

public class Product{
	private String name;
	private double price;
	private int code;
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		if(name.length() > 20) {
			this.name = name.substring(0,19);
		}else {
			this.name = name;
		}
	}
	
	public double getPrice() {
		return price;
	}
	
	public void setPrice(double price) {
		if(price < 0) {
			this.price = 0;
			System.out.println("The product's price should not be less than zero");
		}else {
			this.price = price;
		}
	}
	
	public int getCode(){
		return code;
	}
	
	public void setCode(int code){
		this.code = code;
	}
	
}
