package parking



fun main() {
//    val regex = "[A-Z]{2}-[0-9]{2}-[A-Z]{2}-[0-9]{4}".toRegex()
//    val license = input.drop(1).dropLast(1).first().trim()
    val spots = mutableListOf<Boolean>()
    val mmp = mutableMapOf<Int,String>()
    var isCreated = false
    do {
        val input = readln().split(" ")
        if (isCreated && input.first() != "create") {
            val indexOfPark = spots.indexOfFirst { !it } + 1
            when (input.first()) {
                "spot_by_color" -> {
                    mmp.filter { it.value.split(" ").last().lowercase() == input.last().lowercase() }
                        .map { it.key }.also {
                            if (it.isEmpty()) println("No cars with color ${input.last()} were found.")
                            else  println(it.joinToString())
                        }
                }
                "reg_by_color" -> {
                    mmp.filter { it.value.split(" ").last().lowercase() == input.last().lowercase() }
                        .map { it.value.split(" ").first() }.also {
                            if (it.isEmpty()) println("No cars with color ${input.last()} were found.")
                            else  println(it.joinToString())
                        }
                }
                "spot_by_reg" -> {
                    mmp.filter { it.value.split(" ").first() == input.last() }
                        .map { it.key }.also {
                            if (it.isEmpty()) println("No cars with registration number ${input.last()} were found.")
                            else  println(it.joinToString())
                        }
                }
                "status" -> {
                    if (spots.all { !it }) println("Parking lot is empty.")
                    else mmp.forEach { (t, u) -> println("$t $u") }
                }
                "park" -> {
                    if (spots.all { it }) println("Sorry, the parking lot is full.")
                    else println("${input.last()} car parked in spot ${
                        indexOfPark.also {
                            spots[it - 1] = true
                            mmp[it] = "${input.drop(1).dropLast(1).joinToString("")} ${input.last()}"
                        }
                    }.")
                }

                "leave" -> {
                    try {
                        val index = input.last().toInt() - 1
                        with(index + 1) {
                            if (spots[index]) {
                                spots[index] = false
                                mmp.remove(index+1)
                                println("Spot $this is free.")
                            } else println("There is no car in Spot $this")
                        }

                    } catch (e: IndexOutOfBoundsException) {
                        println("IndexOutOfBounds")
                    }
                }
            }
        }
        else if (input.first() == "create") {
            spots.clear()
            mmp.clear()
            repeat(input.last().toInt()) {
                spots.add(false)
            }
            isCreated = true
            println("Created a parking lot with ${spots.size} spots.")
        }
         else {
            if(input.first()!="exit"){
                println("Sorry, a parking lot has not been created.")
            }
          }
        } while (input.first()!="exit")
}

