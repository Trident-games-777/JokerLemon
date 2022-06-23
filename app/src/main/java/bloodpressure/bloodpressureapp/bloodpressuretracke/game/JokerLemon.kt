package bloodpressure.bloodpressureapp.bloodpressuretracke.game

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import android.view.animation.LinearInterpolator
import androidx.appcompat.app.AppCompatActivity
import androidx.core.animation.doOnEnd
import androidx.core.view.isVisible
import androidx.transition.ChangeBounds
import androidx.transition.Fade
import androidx.transition.Scene
import androidx.transition.TransitionManager
import bloodpressure.bloodpressureapp.bloodpressuretracke.R
import bloodpressure.bloodpressureapp.bloodpressuretracke.databinding.ActivityJokerLemonBinding
import bloodpressure.bloodpressureapp.bloodpressuretracke.databinding.EndSceneBinding
import bloodpressure.bloodpressureapp.bloodpressuretracke.databinding.MiddleSceneBinding
import bloodpressure.bloodpressureapp.bloodpressuretracke.databinding.StartSceneBinding
import kotlin.random.Random

class JokerLemon : AppCompatActivity() {
    private lateinit var globalBinding: ActivityJokerLemonBinding
    private lateinit var startBinding: StartSceneBinding
    private lateinit var middleBinding: MiddleSceneBinding
    private lateinit var endBinding: EndSceneBinding
    private val images = listOf(
        R.drawable.item1,
        R.drawable.item2,
        R.drawable.item3,
        R.drawable.item4,
        R.drawable.item5,
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        globalBinding = ActivityJokerLemonBinding.inflate(layoutInflater)
        startBinding = StartSceneBinding.inflate(layoutInflater)
        middleBinding = MiddleSceneBinding.inflate(layoutInflater)
        endBinding = EndSceneBinding.inflate(layoutInflater)
        setContentView(globalBinding.root)

        val sceneRoot = globalBinding.rootFrame
        val startScene = Scene(sceneRoot, startBinding.root)
        val middleScene = Scene(sceneRoot, middleBinding.root)
        val endScene = Scene(sceneRoot, endBinding.root)
        var first = true
        var victory = setImages()

        val transitionToMiddle = ChangeBounds().apply {
            interpolator = DecelerateInterpolator()
            duration = ENTER_EXIT_DURATION
            addListener(TransitionListener {
                if (victory) {
                    globalBinding.tvWinner.isVisible = true
                    AnimatorSet().apply {
                        playTogether(
                            ObjectAnimator.ofFloat(globalBinding.tvWinner, "scaleX", 0f, 7f),
                            ObjectAnimator.ofFloat(globalBinding.tvWinner, "scaleY", 0f, 7f),
                            ObjectAnimator.ofFloat(globalBinding.tvWinner, "alpha", 1f, 0f)
                        )
                        duration = 1000
                        doOnEnd {
                            globalBinding.tvWinner.isVisible = false
                            globalBinding.button.isEnabled = true
                        }
                        start()
                    }
                } else {
                    globalBinding.button.isEnabled = true
                }
            })
        }

        val transitionToStart = Fade().apply {
            interpolator = LinearInterpolator()
            duration = 0
            addListener(TransitionListener {
                victory = setImages()
                TransitionManager.go(middleScene, transitionToMiddle)
            })
        }

        val transitionToEnd = ChangeBounds().apply {
            interpolator = AccelerateInterpolator()
            duration = ENTER_EXIT_DURATION
            addListener(TransitionListener {
                TransitionManager.go(startScene, transitionToStart)
            })
        }

        globalBinding.button.setOnClickListener {
            if (first) {
                TransitionManager.go(middleScene, transitionToMiddle)
                first = false
            } else {
                TransitionManager.go(endScene, transitionToEnd)
            }
            it.isEnabled = false
        }
    }

    private fun setImages(): Boolean {
        val image1 = images[images.indices.random(Random(System.currentTimeMillis()))]
        startBinding.iv1.setImageResource(image1)
        middleBinding.iv1.setImageResource(image1)
        endBinding.iv1.setImageResource(image1)

        val image2 = images[images.indices.random(Random(System.currentTimeMillis()))]
        startBinding.iv2.setImageResource(image2)
        middleBinding.iv2.setImageResource(image2)
        endBinding.iv2.setImageResource(image2)

        val image3 = images[images.indices.random(Random(System.currentTimeMillis()))]
        startBinding.iv3.setImageResource(image3)
        middleBinding.iv3.setImageResource(image3)
        endBinding.iv3.setImageResource(image3)

        return image1 == image2 && image2 == image3
    }

    companion object {
        private const val ENTER_EXIT_DURATION = 300L
    }
}