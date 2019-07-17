package com.example.bcarlosh.architecturecomponentssample.ui.albumdetail

import android.graphics.Matrix
import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.os.Handler
import android.view.MenuItem
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.bcarlosh.architecturecomponentssample.R
import com.example.bcarlosh.architecturecomponentssample.data.entity.Image
import com.example.bcarlosh.architecturecomponentssample.helpers.GlideApp
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_album_detail.*
import org.koin.androidx.viewmodel.ext.android.getViewModel
import org.koin.core.parameter.parametersOf


class AlbumDetailActivity : AppCompatActivity() {

    private lateinit var viewModel: AlbumDetailViewModel
    private var isStored: Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_album_detail)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        initViewModel()
        setCollapsingToolbarTitle(viewModel.currentAlbumName)
        initFloatingActionButton()
        bindUI()
        setOnFloatingActionButtonClickListener()

        if (savedInstanceState == null) {
            loadFragment(AlbumDetailFragment())
        }
    }

    private fun bindUI() {
        viewModel.albumInfoResponse.observe(this, Observer {
            if (it == null) return@Observer

            if (it.album == null || it.album.tracks.track.isEmpty()) {
                setErrorView()
            } else {
                setAlbumImage(getImageUrl(it.album.image))
                showStoreAlbumFab()
            }
        })

        viewModel.error.observe(this, Observer {
            if (it == null) return@Observer

            setErrorView()
        })

    }

    private fun initViewModel() {
        var artistName = intent?.extras?.let {
            AlbumDetailActivityArgs.fromBundle(it).artistName
        }

        var albumName = intent?.extras?.let {
            AlbumDetailActivityArgs.fromBundle(it).albumName
        }

        if (artistName == null || albumName == null) {
            artistName = EMPTY_NAME
            albumName = EMPTY_NAME
        }
        viewModel = getViewModel { parametersOf(artistName, albumName) }
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().add(R.id.album_detail_container, fragment).commit()
    }

    //region FloatingActionButton functionality
    private fun initFloatingActionButton() {

        viewModel.isStored.observe(this, Observer {
            if (it == null) return@Observer

            this.isStored = it

            if (isStored) {
                store_delete_album_fab.setImageDrawable(getDrawable(R.drawable.ic_delete_store_album))
            } else {
                store_delete_album_fab.setImageDrawable(getDrawable(R.drawable.ic_store_delete_album))
            }
        })
    }

    private fun setOnFloatingActionButtonClickListener() {
        store_delete_album_fab.setOnClickListener {

            if (::viewModel.isInitialized) {
                onFloatingActionButtonClick()
            }
        }
    }

    private fun onFloatingActionButtonClick() {
        isStored = if (isStored) {
            showSnackbar()
            viewModel.deleteAlbum()
            animateFloatingActionButton(isStored)
            false
        } else {
            showSnackbar()
            viewModel.storeAlbum()
            animateFloatingActionButton(isStored)
            true
        }
    }

    private fun showSnackbar() {
        val actionMessage = if (isStored) {
            getString(R.string.album_deleted)
        } else {
            getString(R.string.album_stored)
        }

        Snackbar.make(
            album_detail_activity_root,
            actionMessage,
            Snackbar.LENGTH_SHORT
        ).show()
    }

    private fun animateFloatingActionButton(isStored: Boolean) {
        val animation = store_delete_album_fab.drawable as AnimationDrawable

        if (isStored) {

            animation.start()
            Handler().postDelayed({
                store_delete_album_fab.setImageDrawable(getDrawable(R.drawable.ic_store_delete_album))

                /**
                 * This line is from a work around related to this material design library bug:
                 * https://issuetracker.google.com/issues/111316656
                 */
                store_delete_album_fab.imageMatrix = Matrix()
            }, 200)

        } else {

            animation.start()
            Handler().postDelayed({
                store_delete_album_fab.setImageDrawable(getDrawable(R.drawable.ic_delete_store_album))

                /**
                 * This line is from a work around related to this material design library bug:
                 * https://issuetracker.google.com/issues/111316656
                 */
                store_delete_album_fab.imageMatrix = Matrix()
            }, 200)

        }
    }
    //endregion

    private fun setCollapsingToolbarTitle(text: String) {
        collapsing_toolbar.title = text
    }

    private fun getImageUrl(imageList: List<Image>): String {
        return when {
            imageList.size >= 4 -> imageList[3].text
            imageList.size == 3 -> imageList[2].text
            imageList.size == 2 -> imageList[1].text
            imageList.size == 1 -> imageList[0].text
            else -> ""
        }
    }

    private fun setAlbumImage(imageUrl: String) {
        GlideApp.with(this)
            .load(imageUrl)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(image)
    }

    private fun setErrorView() {
        hideStoreAlbumFab()
        setErrorAlbumImage()
        setAppBarExpanded(false)
    }

    private fun showStoreAlbumFab() {
        store_delete_album_fab.show()
    }

    private fun hideStoreAlbumFab() {
        store_delete_album_fab.hide()
    }

    private fun setAppBarExpanded(boolean: Boolean) {
        app_bar_layout.setExpanded(boolean)
    }

    private fun setErrorAlbumImage() {
        collapsing_toolbar.scrimAnimationDuration = 100
        image.scaleType = ImageView.ScaleType.CENTER_INSIDE
        image.setImageDrawable(getDrawable(R.drawable.ic_sentiment_dissatisfied_64dp))
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }


    companion object {
        private const val EMPTY_NAME = ""
    }

}