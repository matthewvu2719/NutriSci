package model;

import java.util.ArrayList;
import java.util.List;

public class Meal {
	
	int id;
	String name;

	List<Food> food = new ArrayList<Food>();
	double protein;
	double fat;
	double carbs;
	double fibre;
	double sodium;
	double energy;

	
	public void calculateMeal() {
		if(food!=null) {
			for(Food f: food) {
				protein += f.getProtein();
				fat += f.getFat();
				carbs += f.getCarbs();
				fibre += f.getFibre();
				sodium += f.getSodium();
				energy += f.getCal();
			}
		}
	}
	
	public void reset() {
		protein = 0;
		fat = 0;
		carbs = 0;
		fibre = 0;
		sodium = 0;
		energy = 0;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public List<Food> getFood() {
		return food;
	}
	public void setFood(List<Food> food) {
		this.food = food;
	}
	public double getProtein() {
		return protein;
	}
	public void setProtein(double protein) {
		this.protein = protein;
	}
	public double getFat() {
		return fat;
	}
	public void setFat(double fat) {
		this.fat = fat;
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
	public double getSodium() {
		return sodium;
	}
	public void setSodium(double sodium) {
		this.sodium = sodium;
	}
	public double getEnergy() {
		return energy;
	}
	public void setEnergy(double energy) {
		this.energy = energy;
	}
	
	
}
