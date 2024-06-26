package com.example.cloneinstagram

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.example.cloneinstagram.databinding.FragmentPostDetailBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PostDetailFragment : Fragment() {

    lateinit var binding: FragmentPostDetailBinding
    private val imgList = mutableListOf<String>()
    var isLiked : Boolean = false

    //스레드를 활용해서 3초 후 페이지를 넘기기 위한 핸들러
    var mHandler = Handler(Looper.getMainLooper())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPostDetailBinding.inflate(inflater, container, false)

        //데이터 생성
        initData()
        //RecyclerView 생성
        initViewPager(requireActivity().applicationContext)

        val bundle = arguments
        if (bundle != null && bundle.containsKey("key")) {
            // Bundle에서 Serializable 객체를 가져와서 캐스팅
            val homePostData = bundle.getSerializable("key") as HomePostData

            binding.tvPostInstaId.text = homePostData.InstagramID
            binding.tvId.text = homePostData.InstagramID
            binding.tvEditName.text = homePostData.InstagramID
            binding.sivEditProfile.setImageResource(homePostData.ProfileImage)

        }

        binding.ivEditPreviousButton.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        if (binding.tvPostDetailCommentEdit.length() < 26)
        {
            binding.tvPostCommentPlus.visibility = View.GONE
        } else {
            //포스트 댓글의 더보기 버튼을 눌렀을 때 댓글의 나머지 부분 표시
            binding.tvPostCommentPlus.setOnClickListener {
                binding.tvPostCommentPlus.visibility = View.GONE
                binding.tvPostDetailCommentEdit.ellipsize = null
                binding.tvPostDetailCommentEdit.maxLines = Int.MAX_VALUE
            }
        }

        binding.flLike.setOnClickListener {
            if(!isLiked) {
                binding.ivHomeLike.visibility = View.GONE
                binding.ivHomeLikeFilled.visibility = View.VISIBLE
                isLiked = true
            } else {
                binding.ivHomeLike.visibility = View.VISIBLE
                binding.ivHomeLikeFilled.visibility = View.GONE
                isLiked = false
            }
        }

        //스레드를 사용해서 3초후 넘기기
        //Thread(viewPagerPass()).start()

        CoroutineScope(Dispatchers.IO).launch {
            while (true) {
                var count = binding.vpPostPictures.currentItem
                Thread.sleep(3000)

                withContext(Dispatchers.Main){
                    if (count != 2) {
                        count += 1
                        binding.vpPostPictures.currentItem = count
                    } else {
                        binding.vpPostPictures.currentItem = 0
                    }
                }
            }
        }

        return binding.root
    }

    inner class viewPagerPass : Runnable {
        override fun run() {
            while (true) {
                Thread.sleep(3000)
                mHandler.post(viewPagerPassRunnable())
            }
        }

    }

    inner class viewPagerPassRunnable : Runnable {
        override fun run() {
            var count = binding.vpPostPictures.currentItem

            if (count != 2) {
                count += 1
                binding.vpPostPictures.currentItem = count
            } else {
                binding.vpPostPictures.currentItem = 0
            }

        }

    }

    private fun initViewPager(context: Context) {
        binding.vpPostPictures.adapter = PostDetailVPIndicatorAdapter(context, imgList)
        binding.vpPostPictures.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        binding.diImage.attachTo(binding.vpPostPictures)
    }

    private fun initData() {
        imgList.add("https://cdn.pixabay.com/photo/2021/09/28/13/14/cat-6664412_1280.jpg")
        imgList.add("https://cdn.pixabay.com/photo/2020/04/27/09/21/cat-5098930_1280.jpg")
        imgList.add("https://cdn.pixabay.com/photo/2021/09/02/16/48/cat-6593947_1280.jpg")
    }

}