package com.example.test111.bean;

import com.example.test111.validate.constraint.ProductCreateRequestConstraint;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ProductCreateRequestConstraint
@Setter
@ToString
@NoArgsConstructor
public class ErrorResult {
  private String message;
}
