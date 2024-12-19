package ddwu.com.mobileapp.publicsportsfacility

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import ddwu.com.mobileapp.publicsportsfacility.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {

    val detailBinding by lazy {
        ActivityDetailBinding.inflate(layoutInflater)
    }

    var imageUrl: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(detailBinding.root)

        imageUrl = intent.getStringExtra("url")

        Glide.with(this)
            .load(imageUrl)
            .into(detailBinding.imageView)


    }
}
