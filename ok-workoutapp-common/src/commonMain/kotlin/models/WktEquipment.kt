package models

enum class WktEquipment(s: String) {
  NONE("None"),
  OWN_WEIGHT("OwnWeight"),
  DUMBBELLS("Dumbbells"),
  JUMPING_ROPE("JumpingRope"),
  HORIZONTAL_BAR("HorizontalBar"),
  BARS("Bars");

  companion object {
    fun byNameIgnoreCaseOrNull(input: String?): WktEquipment? {
      return WktEquipment.values().firstOrNull { it.name.equals(input, true) }
    }
  }
}