package com.example.workout_tracker.fragment.exercise


import android.graphics.Color
import android.graphics.Typeface
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.text.InputFilter
import android.text.InputType
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.view.setPadding
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.example.workout_tracker.Exceptions.MissValueException
import com.example.workout_tracker.MainActivity
import com.example.workout_tracker.R
import com.example.workout_tracker.util.Exercise
import com.example.workout_tracker.util.Workout
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.android.material.textview.MaterialTextView
import kotlinx.android.synthetic.main.fragment_newtraining.*


class NewTrainingFragment: Fragment(R.layout.fragment_newtraining), View.OnClickListener{
        lateinit var btnContinua : MaterialButton
        lateinit var numeroEsercizi : String
        lateinit var txtNomeWorkout : String
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var v = inflater.inflate(R.layout.fragment_newtraining, container, false)
         btnContinua  = v.findViewById(R.id.button_con)
        btnContinua.setOnClickListener(this)
        return v

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
    override fun onClick(v: View?) {
         txtNomeWorkout = txt_nome_workout.text.toString()
         numeroEsercizi  = txt_numero_esercizi.text.toString()

        if (txtNomeWorkout == ""){
            txt_nome_workout.error = getString(R.string.inserisci_un_valore)
        }else if (numeroEsercizi == ""){
            txt_numero_esercizi.error =  getString(R.string.inserisci_un_valore)
        }else{
            Log.d(null, txtNomeWorkout)
            Log.d(null, numeroEsercizi.toString())
            txt_inserisci_esercizi.text = getString(R.string.inserisci_esercizi)
            btnContinua.visibility = View.INVISIBLE
            textNome.editText!!.isFocusable = false
            textnumes.editText!!.isFocusable =false

            for(i in 0 until numeroEsercizi.toInt()){
                setSerieLayout(i)
            }
            addbutton()
        }
    }


    fun addbutton(){
        val button : MaterialButton = MaterialButton(requireActivity())

        button.text = getString(R.string.fatto)
        val params: LinearLayout.LayoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        button.layoutParams = params

        button.textSize = 24f
        button.setPadding(50)
         button.setOnClickListener {
            try {

                var workout = Workout(txtNomeWorkout)
                for (i in 0 until (numeroEsercizi.toInt()*2) step 2){
                    Log.d(null, "$i \n")
                 workout.addExercise(getExerciseFromLinearLayout(i))

                }
                // add workout to firebase
                val act =activity as MainActivity
                act.addWorkoutToFirebase(workout)
                //clear activity
                object : CountDownTimer(500, 1000) {
                    override fun onTick(millisUntilFinished: Long) {

                    }

                    override fun onFinish() {
                        act.supportFragmentManager.commit { setReorderingAllowed(true)
                            replace<NewTrainingFragment>(R.id.main_fragment)

                        }
                    }


                }.start()

            }catch (e: Exception){
                Toast.makeText(activity, e.message, Toast.LENGTH_SHORT).show()
            }



         }
        linear_layout_add_exercise.gravity = Gravity.CENTER
        linear_layout_add_exercise.addView(button)
    }

    private fun getExerciseFromLinearLayout(i: Int) : Exercise{
        val nomeEsercizio : String
        val nSerie : String
        val nRep : String
        val recupero : String
        val linearLayout = linear_layout_add_exercise.getChildAt(i) as LinearLayout
        val linearLayout2 = linear_layout_add_exercise.getChildAt(i + 1) as LinearLayout
        val textInputNome = linearLayout.getChildAt(1) as TextInputLayout
        val textInputSerie = linearLayout2.getChildAt(0) as TextInputLayout
        val textInputRep = linearLayout2.getChildAt(1) as TextInputLayout
        val textInputRec= linearLayout2.getChildAt(2) as TextInputLayout
        nomeEsercizio = textInputNome.editText!!.text.toString()
        nSerie = textInputSerie.editText!!.text.toString()
        nRep = textInputRep.editText!!.text.toString()
        recupero= textInputRec.editText!!.text.toString()

        if((nomeEsercizio == "")  or (nSerie == "" )or (nRep == "") or ( recupero == "")){
            throw  MissValueException(getString(R.string.riempi_campi))
        }
       return Exercise(nomeEsercizio, nSerie.toInt(), nRep.toInt(), recupero.toInt())

    }

    fun setSerieLayout(i: Int){
        var firstLinearLayout : LinearLayout = LinearLayout(requireActivity())
        var linearLayout : LinearLayout = LinearLayout(requireActivity())
        linear_layout_add_exercise.addView(firstLinearLayout)
        linear_layout_add_exercise.addView(linearLayout)


        var textView2: MaterialTextView =  MaterialTextView(requireActivity())
        textView2.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        textView2.text="${i+1}° Esercizio"

        textView2.gravity = Gravity.CENTER
        textView2.textSize = 18f
        textView2.typeface = Typeface.DEFAULT_BOLD

        var textInputLayout = TextInputLayout(requireActivity())
        textInputLayout.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1F)
        textInputLayout.boxBackgroundColor = Color.TRANSPARENT

        textInputLayout.boxBackgroundMode =  TextInputLayout.BOX_BACKGROUND_OUTLINE
        textInputLayout.hint = "N°Serie"
        textInputLayout.boxStrokeColor = Color.RED
        textInputLayout

        var editText = TextInputEditText(textInputLayout.context)

        editText.inputType = (InputType.TYPE_CLASS_NUMBER )
        editText.filters += InputFilter.LengthFilter(2)
        textInputLayout.setPadding(25, 0, 25, 0)
        textInputLayout.addView(editText, LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT))


        var textInputLayout2 = TextInputLayout(requireActivity())
        textInputLayout2.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1F)
        textInputLayout2.boxStrokeColor = Color.RED
        textInputLayout2.boxBackgroundColor = Color.TRANSPARENT
        textInputLayout2.boxBackgroundMode =  TextInputLayout.BOX_BACKGROUND_OUTLINE
        textInputLayout2.hint ="N°Rep"
        var editText2 = TextInputEditText(textInputLayout2.context)
        editText2.inputType = (InputType.TYPE_CLASS_NUMBER  )
        editText2.filters += InputFilter.LengthFilter(3)

        textInputLayout2.setPadding(25, 0, 25, 0)
        textInputLayout2.addView(editText2, LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT))

        var textInputLayout3 = TextInputLayout(requireActivity())
        textInputLayout3.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1F)
        textInputLayout3.boxStrokeColor = Color.RED
        textInputLayout3.boxBackgroundColor = Color.TRANSPARENT
        textInputLayout3.boxBackgroundMode =  TextInputLayout.BOX_BACKGROUND_OUTLINE
        textInputLayout3.hint ="Recupero"
        var editText3 = TextInputEditText(textInputLayout3.context)
        editText3.inputType = (InputType.TYPE_CLASS_NUMBER )
        editText3.filters += InputFilter.LengthFilter(4)

        textInputLayout3.setPadding(25, 0, 25, 0)
        textInputLayout3.addView(editText3, LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT))

        var textInputLayout4 = TextInputLayout(requireActivity())
        textInputLayout4.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1F)
        textInputLayout4.boxStrokeColor = Color.RED
        textInputLayout4.boxBackgroundColor = Color.TRANSPARENT
        textInputLayout4.boxBackgroundMode =  TextInputLayout.BOX_BACKGROUND_FILLED
        textInputLayout4.hint ="Nome esercizio"
        var editText4 = TextInputEditText(textInputLayout4.context)

        editText4.filters += InputFilter.LengthFilter(24)

        textInputLayout4.setPadding(25, 50, 25, 25)
        textInputLayout4.addView(editText4, LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT))



        firstLinearLayout.addView(textView2)
        firstLinearLayout.addView(textInputLayout4)
        linearLayout.addView(textInputLayout)
        linearLayout.addView(textInputLayout2)
        linearLayout.addView(textInputLayout3)


    }




}