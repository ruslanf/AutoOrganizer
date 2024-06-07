package org.bz.autoorganizer.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

abstract class BaseVMFragment<VB: ViewBinding> : Fragment() {

    abstract val inflate: Inflate<VB>

    protected var binding: VB? = null
    protected var fragmentTag: String = this::class.java.simpleName

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = inflate.invoke(inflater, container, false)
        return binding?.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    open fun onBackPressed(): Boolean = true
}