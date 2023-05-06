package uz.data.studentapi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.gson.Gson
import okhttp3.*
import java.io.IOException

class MainActivity : AppCompatActivity() {

    lateinit var recucler: RecyclerView
    lateinit var swipeRefresh: SwipeRefreshLayout

    val httpClient = OkHttpClient()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recucler = findViewById(R.id.recucler)
        swipeRefresh = findViewById(R.id.swipeRefresh)

        swipeRefresh.setOnRefreshListener {
            fetch()
        }

        val button: Button = findViewById(R.id.button)

        recucler.layoutManager = LinearLayoutManager(this)

        fetch()
    }

    private fun fetch() {
        val request = Request.Builder()
            .url("https://efficacious-chiseled-larkspur.glitch.me/api/students")
            .build()

        val handler = Handler()

        httpClient.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Toast.makeText(baseContext, "Failed on fetch: $e", Toast.LENGTH_SHORT).show()
                swipeRefresh.isRefreshing = false
            }

            override fun onResponse(call: Call, response: Response) {
                val body: String = response.body!!.string()
                val students: List<Student> = Gson().fromJson(body, StudentList::class.java)

                handler.post {
                    recucler.adapter = StudentAdapter(students)
                    swipeRefresh.isRefreshing = false
                }
            }
        })
    }
}