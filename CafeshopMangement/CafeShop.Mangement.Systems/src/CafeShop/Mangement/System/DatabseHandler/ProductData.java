package CafeShop.Mangement.System.DatabseHandler;

public class ProductData {

	private Integer id;
	private String prod_Id;
	private String prod_Name;
	private String Stock;
	private String status;
	private String image;
	private String type;
	private Double price;
	private Integer quantity;
	private java.util.Date Date;

	public ProductData(String prod_Id, String prod_Name, String Stock, String status, String type,String image,
			Double price, java.util.Date Date, Integer id) {
		this.id = id;
		this.prod_Id = prod_Id;
		this.prod_Name = prod_Name;
		this.Stock = Stock;
		this.status = status;
		this.type=type;
		this.image = image;
		this.price = price;
		this.Date = Date;

	}
	public ProductData(Integer id,String prod_id, String prod_Name,String type,Double price, String image,java.util.Date date,Integer quantity) {
		this.id=id;
		this.prod_Id=prod_id;
		this.prod_Name=prod_Name;
		this.price=price;
		this.image=image;
		this.quantity=quantity;
		this.Date=date;
		this.type=type;
		
	}

	public Integer getId() {
		return id;
	}

	public String getProd_Id() {
		return prod_Id;
	}

	public String getProd_Name() {
		return prod_Name;
	}

	public String getStock() {
		return Stock;
	}

	public String getStatus() {
		return status;
	}

	public String getImage() {
		return image;
	}

	public Double getPrice() {
		return price;
	}

	public java.util.Date getDate() {
		return Date;
	}

	public String getType() {
		return type;
	}
	public Integer getQuantity() {
		return quantity;
	}

}
