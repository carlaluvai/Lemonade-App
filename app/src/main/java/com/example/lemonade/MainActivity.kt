/*
 * Copyright (C) 2021 The Android Open Source Project.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.lemonade

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.example.lemonade.Constants.Companion.DRINK
import com.example.lemonade.Constants.Companion.LEMONADE_STATE
import com.example.lemonade.Constants.Companion.LEMON_SIZE
import com.example.lemonade.Constants.Companion.RESTART
import com.example.lemonade.Constants.Companion.SELECT
import com.example.lemonade.Constants.Companion.SQUEEZE
import com.example.lemonade.Constants.Companion.SQUEEZE_COUNT
import com.example.lemonade.databinding.ActivityMainBinding


import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    /**
     * DO NOT ALTER ANY VARIABLE OR VALUE NAMES OR THEIR INITIAL VALUES.
     *
     * Anything labeled var instead of val is expected to be changed in the functions but DO NOT
     * alter their initial values declared here, this could cause the app to not function properly.
     */
    private lateinit var textAction: TextView

    private var lemonTree = LemonTree()
    private lateinit var lemonImage: ImageView
    private lateinit var lemonadeState: String

    // Default lemonSize to -1
    var lemonSize = -1

    // Default the squeezeCount to -1
    var squeezeCount = -1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        textAction = binding.textAction
        lemonImage = binding.imageLemonState
        lemonadeState = getString(R.string.default_select)




        // === DO NOT ALTER THE CODE IN THE FOLLOWING IF STATEMENT ===
        if (savedInstanceState != null) {
            lemonadeState = savedInstanceState.getString(
                LEMONADE_STATE, "select"
            )
            lemonSize = savedInstanceState.getInt(LEMON_SIZE, -1)
            squeezeCount = savedInstanceState.getInt(SQUEEZE_COUNT, -1)
        }
        // === END IF STATEMENT ===


        setViewElements()
        lemonImage.setOnClickListener {
            clickLemonImage()


        }
        lemonImage.setOnLongClickListener {
            showSnackbar()

        }

    }


    /**
     * === DO NOT ALTER THIS METHOD ===
     *
     * This method saves the state of the app if it is put in the background.
     */
    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString(LEMONADE_STATE, lemonadeState)
        outState.putInt(LEMON_SIZE, lemonSize)
        outState.putInt(SQUEEZE_COUNT, squeezeCount)
        super.onSaveInstanceState(outState)
    }

    /**
     * Clicking will elicit a different response depending on the state.
     * This method determines the state and proceeds with the correct action.
     */
    private fun clickLemonImage() {
        // TODO: use a conditional statement like 'if' or 'when' to track the lemonadeState
        //  when the the image is clicked we may need to change state to the next step in the
        //  lemonade making progression (or at least make some changes to the current state in the
        //  case of squeezing the lemon). That should be done in this conditional statement

        when (lemonadeState) {

            SELECT -> {
                // TODO: When the image is clicked in the SELECT state, the state should become SQUEEZE
                //  - The lemonSize variable needs to be set using the 'pick()' method in the LemonTree class
                //  - The squeezeCount should be 0 since we haven't squeezed any lemons just yet
                lemonadeState = SQUEEZE
                lemonSize = lemonTree.pick()
                squeezeCount = 0


            }
            SQUEEZE -> {
                // TODO: When the image is clicked in the SQUEEZE state the squeezeCount needs to be
                //  INCREASED by 1 and lemonSize needs to be DECREASED by 1.
                //  - If the lemonSize has reached 0, it has been juiced and the state should become DRINK
                //  - Additionally, lemonSize is no longer relevant and should be set to -1
                squeezeCount++

                lemonSize--
                if (lemonSize == 0) {
                    lemonadeState = DRINK

                }

            }
            DRINK -> {
                // TODO: When the image is clicked in the DRINK state the state should become RESTART
                lemonadeState = RESTART
                lemonSize = -1

            }
            else -> {
                // TODO: When the image is clicked in the RESTART state the state should become SELECT
                lemonadeState = SELECT

            }

        }
        setViewElements()
    }


    /**
     * Set up the view elements according to the state.
     */
    private fun setViewElements() {

        // TODO: set up a conditional that tracks the lemonadeState

        // TODO: for each state, the textAction TextView should be set to the corresponding string from
        //  the string resources file. The strings are named to match the state

        // TODO: Additionally, for each state, the lemonImage should be set to the corresponding
        //  drawable from the drawable resources. The drawables have the same names as the strings
        //  but remember that they are drawables, not strings.

        when (lemonadeState) {
            SELECT -> {
                updateElements(getString(R.string.lemon_select), R.drawable.lemon_tree)
            }
            SQUEEZE -> {
                updateElements(getString(R.string.lemon_squeeze), R.drawable.lemon_squeeze)
            }
            DRINK -> {
                updateElements(getString(R.string.lemon_drink), R.drawable.lemon_drink)
            }
            RESTART -> {
                updateElements(getString(R.string.lemon_empty_glass), R.drawable.lemon_restart)
            }
        }


    }

    private fun updateElements(state: String, image: Int) {
        textAction.text = state
        lemonImage.setImageResource(image)
    }

    /**
     * === DO NOT ALTER THIS METHOD ===
     *
     * Long clicking the lemon image will show how many times the lemon has been squeezed.
     */
    private fun showSnackbar(): Boolean {
        if (lemonadeState != SQUEEZE) {
            return false
        }
        val squeezeText = getString(R.string.squeeze_count, squeezeCount)
        Snackbar.make(
            binding.constraintLayout,
            squeezeText,
            Snackbar.LENGTH_SHORT
        ).show()
        return true
    }
}

/**
 * A Lemon tree class with a method to "pick" a lemon. The "size" of the lemon is randomized
 * and determines how many times a lemon needs to be squeezed before you get lemonade.
 */
class LemonTree {
    fun pick(): Int {
        return (2..4).random()
    }
}
