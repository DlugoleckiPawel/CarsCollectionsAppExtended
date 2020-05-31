package validator;

import model.Car;
import validator.generic.AbstractValidator;

import java.math.BigDecimal;
import java.util.Map;

public class CarValidator extends AbstractValidator<Car> {

	private static final String MODEL_ERROR_MESSAGE = "model should contain only upper case letters and spaces";
	private static final String COLOR_ERROR_MESSAGE = "color object cannot be null";
	private static final String MILEAGE_ERROR_MESSAGE = "mileage value should have positive value";
	private static final String PRICE_ERROR_MESSAGE = "price value should have positive value";
	private static final String COMPONENTS_ERROR_MESSAGE = "components collection should contain elements with upper case letters and spaces";
	private static final String ENGINE_POWER_ERROR_MESSAGE = "engine power should have positive double value";
	private static final String ENGINE_TYPE_ERROR_MESSAGE = "engine type object cannot be null";
	private static final String TYRE_TYPE_ERROR_MESSAGE = "tyre type object cannot be null";
	private static final String CAR_BODY_TYPE_ERROR_MESSAGE = " car body type object cannot be null";


	@Override
	public Map<String, String> validate(Car car) {

		errors.clear();

		if (!isModelValid(car)) {
			errors.put("MODEL", MODEL_ERROR_MESSAGE);
		}

		if (!isColorValid(car)) {
			errors.put("COLOR", COLOR_ERROR_MESSAGE);
		}

		if (!isMileageValid(car)) {
			errors.put("MILEAGE", MILEAGE_ERROR_MESSAGE);
		}

		if (!isPriceValid(car)) {
			errors.put("PRICE", PRICE_ERROR_MESSAGE);
		}

		if (!isComponentsCollectionValid(car)) {
			errors.put("COMPONENTS", COMPONENTS_ERROR_MESSAGE);
		}
		if (!isEnginePowerValid(car)) {
			errors.put("ENGINE POWER", ENGINE_POWER_ERROR_MESSAGE);
		}
		if (!isEngineTypeValid(car)) {
			errors.put("ENGINE TYPE", ENGINE_TYPE_ERROR_MESSAGE);
		}
		if (!isTyreTypeValid(car)) {
			errors.put("TYRE TYPE", TYRE_TYPE_ERROR_MESSAGE);
		}
		if (!isCarBodyTypeValid(car)) {
			errors.put("CAR BODY TYPE", CAR_BODY_TYPE_ERROR_MESSAGE);
		}
		return errors;
	}

	private static boolean isEngineTypeValid(Car car) {
		return car.getEngine().getType() != null;
	}

	private static boolean isTyreTypeValid(Car car) {
		return car.getWheel().getType() != null;
	}

	private static boolean isCarBodyTypeValid(Car car) {
		return car.getCarBody().getType() != null;
	}

	private static boolean isEnginePowerValid(Car car) {
		return car.getEngine().getPower() > 0 && String.valueOf(car.getEngine().getPower()).matches("(\\d)+.(\\d)+");
	}

	private static boolean isModelValid(Car car) {
		return car.getModel() != null && car.getModel().matches("[A-Z\\s]+");
	}

	private static boolean isColorValid(Car car) {
		return car.getCarBody().getColor() != null;
	}


	private static boolean isMileageValid(Car car) {
		return car.getMileage() > 0;
	}

	private static boolean isPriceValid(Car car) {
		return car.getPrice().compareTo(BigDecimal.ZERO) > 0;
	}

	private static boolean isComponentsCollectionValid(Car car) {
		return car.getCarBody().getComponents() != null && car.getCarBody().getComponents().stream().allMatch(c -> c.matches("[A-Z\\s]+"));
	}

}
