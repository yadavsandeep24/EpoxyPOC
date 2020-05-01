package com.zeptolearn.epoxypoc.ui.main

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.airbnb.epoxy.EpoxyVisibilityTracker
import com.airbnb.epoxy.addGlidePreloader
import com.airbnb.epoxy.carousel
import com.airbnb.epoxy.glidePreloader
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.signature.ObjectKey
import com.zeptolearn.epoxypoc.AppExecutors
import com.zeptolearn.epoxypoc.R
import com.zeptolearn.epoxypoc.databinding.FragmentMainBinding
import com.zeptolearn.epoxypoc.di.Injectable
import com.zeptolearn.epoxypoc.ui.main.models.CustomCarouselItem_
import com.zeptolearn.epoxypoc.ui.main.models.ItemEpoxyHolder_
import com.zeptolearn.epoxypoc.ui.main.models.itemEpoxyHolder
import com.zeptolearn.epoxypoc.util.Status
import com.zeptolearn.epoxypoc.util.autoCleared
import javax.inject.Inject

/**
 * A placeholder fragment containing a simple view.
 */
class PlaceholderFragment : Fragment(), Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    val pageViewModel: PageViewModel by viewModels {
        viewModelFactory
    }

    @Inject
    lateinit var appExecutors: AppExecutors

    var binding by autoCleared<FragmentMainBinding>()

    @SuppressLint("ResourceType")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.getInt(ARG_SECTION_NUMBER)?.let {
            pageViewModel.setContainerPosition(
                it
            )
        }
        binding.lifecycleOwner = viewLifecycleOwner
       // binding.container = pageViewModel.containers
        subscribeUI()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val dataBinding = DataBindingUtil.inflate<FragmentMainBinding>(
            inflater,
            R.layout.fragment_main,
            container,
            false
        )
        binding = dataBinding
        return binding.root
    }

    companion object {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private const val ARG_SECTION_NUMBER = "section_number"

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        @JvmStatic
        fun newInstance(position: Int): PlaceholderFragment {
            return PlaceholderFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_SECTION_NUMBER, position)
                }
            }
        }
    }

    private fun subscribeUI() {
        pageViewModel.containers.observe(viewLifecycleOwner, Observer {
            if(it.status == Status.LOADING){
                binding.progressBar.visibility = View.VISIBLE
            }else{
                binding.progressBar.visibility = View.GONE
            }
            if(it?.data != null) {
                val contaners = it.data!!
                binding.recyclerView.setBackgroundColor(Color.parseColor(contaners.Containers[1].bgcolor))
                binding.recyclerView.layoutManager =
                    GridLayoutManager(activity, contaners.Containers[1].ColCount)
                binding.recyclerView.setHasFixedSize(true)
                val epoxyVisibilityTracker = EpoxyVisibilityTracker()
                epoxyVisibilityTracker.attach(binding.recyclerView)
                binding.recyclerView.addGlidePreloader(
                    Glide.with(this),
                    preloader = glidePreloader { requestManager, model: ItemEpoxyHolder_, _ ->
                        requestManager.loadImage(model.imageUrl)
                    }
                )
                binding.recyclerView.withModels {
                    carousel {
                        id("This is a ViewPager.")
                        hasFixedSize(true)
                        onBind { _, view, _ ->
                            view.setBackgroundColor(Color.parseColor(contaners.Containers[0].bgcolor))
                        }
                        models(contaners.Containers[0].data.mapIndexed { _, item ->
                            CustomCarouselItem_()
                                .id(item.id)
                                .title(item.title)
                                .imageUrl(item.bgurl)
                                .titleColor(item.titleColor)
                                .listener {
                                    Toast.makeText(
                                        activity,
                                        "Card-->" + item.title,
                                        Toast.LENGTH_SHORT
                                    )
                                        .show()
                                }
                        })
                    }
                    for (photo in contaners.Containers[1].data) {

                        itemEpoxyHolder {
                            id(photo.id)
                            title(photo.title)
                            imageUrl(photo.bgurl)
                            titleColor(photo.titleColor)
                            listener {
                                Toast.makeText(
                                    activity,
                                    "clicked-->" + photo.title,
                                    Toast.LENGTH_SHORT
                                )
                                    .show()
                            }
                        }
                    }
                }
            }

        })

    }
}


fun RequestManager.loadImage(url: String): RequestBuilder<Bitmap> {

    val options = RequestOptions
        .diskCacheStrategyOf(DiskCacheStrategy.RESOURCE)
        .dontAnimate()
        .placeholder(R.drawable.cover5)
        .signature(ObjectKey(url.plus("preloading")))

    return asBitmap()
        .apply(options)
        .load(url)
}