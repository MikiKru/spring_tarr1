package pl.tarr1.spring_app.model.enums;

public enum Category {
    IT("IT"),
    DEV_OPS("DEV OPS"),
    BACK_END("BACK END"),
    FRONT_END("FRONT END");

    String categoryName;
    Category(String categoryName) {
        this.categoryName = categoryName;
    }
}
