package main.controller;

public enum Button {

  A(0),
  B(1),
  X(2),
  Y(3),

  FRONT_LEFT(4),
  FRONT_RIGHT(5),

  SPECIAL_BACK(6),
  SPECIAL_START(7),

  MUSHROOM_LEFT(8),
  MUSHROOM_RIGHT(9),

  UP(10),
  RIGHT(11),
  DOWN(12),
  LEFT(13);

  final int index;

  Button(int index) {
    this.index = index;
  }

}
