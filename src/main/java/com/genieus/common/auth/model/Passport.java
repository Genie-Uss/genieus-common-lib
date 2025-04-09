package com.genieus.common.auth.model;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Passport implements Serializable {
  private static final long serialVersionUID = 1L;
  private Long userId;
  private RoleType role;
}
