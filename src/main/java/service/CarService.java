package service;

import exceptions.ExceptionCode;
import exceptions.MyException;
import json.impl.CarsConverter;
import lombok.Data;
import model.Car;
import model.enums.CarBodyType;
import model.enums.EngineType;
import model.enums.SortingType;
import model.enums.TyreType;
import org.eclipse.collections.impl.collector.BigDecimalSummaryStatistics;
import org.eclipse.collections.impl.collector.Collectors2;
import validator.CarValidator;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Data
public class CarService {
	private final List<Car> cars;

	public CarService(String filename) {
		this.cars = getCarsFromJson(filename);

	}

	private List<Car> getCarsFromJson(String filename) {
		AtomicInteger atomicInteger = new AtomicInteger(1);
		CarValidator carValidation = new CarValidator();

		return new CarsConverter("src/main/resources/" + filename + ".json")
				.fromJson()
				.orElseThrow(() -> new MyException(ExceptionCode.SERVICE, "CAR SERVICE FROM JSON EXCEPTION"))
				.stream()
				.filter(car -> {

					Map<String, String> errors = carValidation.validate(car);
					if (carValidation.hasErrors()) {
						System.out.println("VALIDATION ERRORS FOR CAR NO, " + atomicInteger.get());
						errors.forEach((k, v) -> System.out.println(k + " " + v));
					}
					atomicInteger.incrementAndGet();
					return !carValidation.hasErrors();
				}).collect(Collectors.toList());


	}

	public List<Car> sort(SortingType type, boolean descending) {

		Stream<Car> carStream = null;

		switch (type) {

			case NUMBER_OF_COMPONONENTS -> cars.stream().sorted(Comparator.comparing(car -> car.getCarBody().getComponents().size()));
			case TYRE_SIZE -> carStream = cars.stream().sorted(Comparator.comparing(car -> car.getWheel().getSize()));
			case ENGINE_POWER -> carStream = cars.stream().sorted(Comparator.comparing(car -> car.getEngine().getPower()));
		}

		List<Car> sortedCars = carStream.collect(Collectors.toList());
		if (descending) {
			Collections.reverse(sortedCars);
		}

		return sortedCars;
	}

	public List<Car> getCarsWithSpecifiedBodyTypeInThePriceRange(CarBodyType type, BigDecimal minPrice, BigDecimal maxPrice) {
		if (minPrice.compareTo(maxPrice) >= 0) {
			throw new MyException(ExceptionCode.INPUT_DATA, "Min price can not be greater than the max price");
		}
		return cars
				.stream()
				.filter(car -> car.getCarBody().getType().equals(type))
				.filter(car -> car.getPrice().compareTo(minPrice) > 0 && car.getPrice().compareTo(maxPrice) < 0)
				.collect(Collectors.toList());
	}

	public List<Car> sortedCarModelForSpecifiedEngineType(EngineType engineType) {
		return cars
				.stream()
				.filter(e -> e.getEngine().getType().equals(engineType))
				.sorted(Comparator.comparing(Car::getModel))
				.collect(Collectors.toList());
	}

	public void statistics() {
		System.out.println("- - - - PRICE - - - -");
		BigDecimalSummaryStatistics priceStatistics = cars
				.stream()
				.collect(Collectors2.summarizingBigDecimal(Car::getPrice));
		System.out.println("MAX = " + priceStatistics.getMax());
		System.out.println("MIN = " + priceStatistics.getMin());
		System.out.println("AVG = " + priceStatistics.getAverage());

		System.out.println("- - - - MILEAGE - - - - ");
		DoubleSummaryStatistics mileageStatistics = cars
				.stream()
				.collect(Collectors.summarizingDouble(Car::getMileage));
		System.out.println("MAX = " + mileageStatistics.getMax());
		System.out.println("MIN = " + mileageStatistics.getMin());
		System.out.println("AVG = " + mileageStatistics.getAverage());

		System.out.println("- - - - POWER_ENGINE - - - -");
		DoubleSummaryStatistics powerStatistics = cars
				.stream()
				.collect(Collectors.summarizingDouble(car -> car.getEngine().getPower()));
		System.out.println("MAX = " + powerStatistics.getMax());
		System.out.println("MIN = " + powerStatistics.getMin());
		System.out.println("AVG = " + powerStatistics.getAverage());
	}

	public Map<Car, Integer> getCarsWithTheirMileage() {
		return cars
				.stream()
				.sorted(Comparator.comparing(Car::getMileage).reversed())
				.collect(Collectors.toMap(
						Function.identity(),
						Car::getMileage,
						(v1, v2) -> v1,
						LinkedHashMap::new));
	}

	public Map<TyreType, List<Car>> getCarsWithTyreType() {
		return cars
				.stream()
				.collect(Collectors.groupingBy(
						t -> t.getWheel().getType()))
				.entrySet()
				.stream()
				.sorted(Comparator.comparing(tyre -> tyre.getValue().size(), Comparator.reverseOrder()))
				.collect(Collectors.toMap(
						k -> k.getKey(),
						k -> k.getValue(),
						(v1, v2) -> v1,
						LinkedHashMap::new));
	}

	public List<Car> getCarsWithSpecifiedComponent(List<String> components) {
		return cars
				.stream()
				.filter(car -> car.getCarBody().getComponents().containsAll(components))
				.sorted(Comparator.comparing(Car::getModel).reversed())
				.collect(Collectors.toList());
	}
}
