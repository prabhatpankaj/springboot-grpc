package com.techbellys.clientservice.dto;

import com.techbellys.messaging.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class PersonClient implements Serializable {
	private String name;
	private Integer age;
	private Gender gender;
}
