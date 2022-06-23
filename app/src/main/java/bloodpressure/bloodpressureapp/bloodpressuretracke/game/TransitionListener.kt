package bloodpressure.bloodpressureapp.bloodpressuretracke.game

import androidx.transition.Transition

class TransitionListener(val doOnEnd: () -> Unit) : Transition.TransitionListener {
    override fun onTransitionStart(transition: Transition) {
    }

    override fun onTransitionEnd(transition: Transition) {
        doOnEnd()
    }

    override fun onTransitionCancel(transition: Transition) {
    }

    override fun onTransitionPause(transition: Transition) {
    }

    override fun onTransitionResume(transition: Transition) {
    }
}