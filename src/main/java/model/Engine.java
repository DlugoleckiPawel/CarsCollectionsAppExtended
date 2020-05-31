package model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import model.enums.EngineType;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Engine {
	private EngineType type;
	private double power;
}
