package model;

public class Food {
	String description;
	int foodId;
	int foodGroupId;
	double protein;
	double carbs;
	double fibre;
	double fat;
	double sodium;
	double cal;
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getDescription() {
		return description;
	}
	
	@Override
    public String toString() {
        return this.description; // or whatever field contains the display name
    }

	public void setFoodGroupId(int foodGroupId) {
		this.foodGroupId = foodGroupId;
	}
	
	public int getFoodGroupId() {
		return foodGroupId;
	}
	
	public void setFoodId(int id) {
		foodId = id;
	}
	
	public int getFoodId() {
		return foodId;
	}
	
	public double getProtein() {
		return protein;
	}

	public void setProtein(double protein) {
		this.protein = protein;
	}

	public double getCarbs() {
		return carbs;
	}

	public void setCarbs(double carbs) {
		this.carbs = carbs;
	}

	public double getFibre() {
		return fibre;
	}

	public void setFibre(double fibre) {
		this.fibre = fibre;
	}

	public double getFat() {
		return fat;
	}

	public void setFat(double fat) {
		this.fat = fat;
	}

	public double getSodium() {
		return sodium;
	}

	public void setSodium(double sodium) {
		this.sodium = sodium;
	}

	public double getCal() {
		return cal;
	}

	public void setCal(double cal) {
		this.cal = cal;
	}
}

