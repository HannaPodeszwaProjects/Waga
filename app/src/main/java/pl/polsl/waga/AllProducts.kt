package pl.polsl.waga

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class AllProducts : AppCompatActivity() {
    private lateinit var labelsList :ArrayList<String>
    private var imagesList = arrayListOf(R.drawable.jablko, R.drawable.banan, R.drawable.burak, R.drawable.kapusta,
        R.drawable.karambola, R.drawable.marchewka, R.drawable.ogorek, R.drawable.guawa, R.drawable.kiwi,
        R.drawable.mango, R.drawable.melon, R.drawable.cebula_czerwona, R.drawable.cebula_biala, R.drawable.pomarancza, R.drawable.pietruszka,
        R.drawable.brzoskwinia, R.drawable.gruszka, R.drawable.papryka_czerwona, R.drawable.persymona, R.drawable.papaja, R.drawable.sliwka, R.drawable.granat, R.drawable.ziemniak, R.drawable.pomidor)
    private var labelsNumber = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_products)

        val exitButton: Button = findViewById(R.id.exitButton)
        exitButton.setOnClickListener {
            val myIntent = Intent(this, MainActivity::class.java)
            startActivity(myIntent)
        }


        val args = intent.getBundleExtra("BUNDLE")
        labelsList = args!!.getSerializable("labellist") as ArrayList<String>
        labelsNumber = labelsList.size
        createLayoutDynamically(labelsNumber)
    }
    private fun createLayoutDynamically(buttonsNumber: Int) {
        val baseLayout = findViewById<LinearLayout>(R.id.linearLayout)

        for (i in 0..buttonsNumber) {
            val layoutH2 = LinearLayout(this)
            baseLayout.addView(layoutH2)

            for(j in 0..2)
            {
                if(i*3+j == buttonsNumber)
                {
                    return
                }

                val myButton = ImageButton(this)

                myButton.setImageResource(imagesList[(i*3)+j])
                myButton.id = i*3 + j

                myButton.x = 10.0f + j*25.0f
                myButton.y = 10.0f

                layoutH2.addView(myButton)

                myButton.setOnClickListener {
                   val toast = Toast.makeText(applicationContext, "Drukowanie etykiety dla " + labelsList[i*3+j], Toast.LENGTH_SHORT)
                    toast.show()
                    val myIntent = Intent(this, MainActivity::class.java)
                    startActivity(myIntent)

                }
            }
        }
    }
}