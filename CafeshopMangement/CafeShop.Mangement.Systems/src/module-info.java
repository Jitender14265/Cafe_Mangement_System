module Cafe {
	exports CafeShop.Mangement.System.Main;
    opens CafeShop.Mangement.System.Main;
    
    exports CafeShop.Mangement.System.Menu;
    opens CafeShop.Mangement.System.Menu;
    
    opens CafeShop.Mangement.System.DatabseHandler to javafx.base;
	
	
	requires javafx.controls;
	requires java.sql;
	requires java.desktop;
	requires javafx.fxml;
	
	opens  CafeShop.Mangement.System.Login to javafx.graphics, javafx.fxml;
}
