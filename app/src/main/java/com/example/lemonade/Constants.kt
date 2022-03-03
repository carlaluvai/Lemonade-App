package com.example.lemonade


class Constants {

    companion object {

        const val LEMONADE_STATE: String = R.string.state.toString()
        const val LEMON_SIZE: String = R.string.size.toString()
        const val SQUEEZE_COUNT: String = R.string.count.toString()

        // SELECT represents the "pick lemon" state
//        const val SELECT: String = R.string.default_select.toString()
        const val SELECT = "select"

        // SQUEEZE represents the "squeeze lemon" state
        const val SQUEEZE: String = R.string.squeeze.toString()

        // DRINK represents the "drink lemonade" state
        const val DRINK: String = R.string.drink.toString()

        // RESTART represents the state where the lemonade has be drunk and the glass is empty
        const val RESTART: String = R.string.restart.toString()




    }
}