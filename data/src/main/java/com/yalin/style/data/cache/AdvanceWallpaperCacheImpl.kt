package com.yalin.style.data.cache

import android.text.TextUtils
import com.yalin.style.data.entity.AdvanceWallpaperEntity
import javax.inject.Inject
import javax.inject.Singleton

/**
 * @author jinyalin
 * @since 2017/8/4.
 */
@Singleton
class AdvanceWallpaperCacheImpl @Inject constructor() : AdvanceWallpaperCache {
    private var wallpapers: List<AdvanceWallpaperEntity>? = null

    override fun put(wallpapers: List<AdvanceWallpaperEntity>) {
        this.wallpapers = wallpapers
    }

    override fun getSelectedWallpaper(): AdvanceWallpaperEntity {
        if (isDirty()) {
            throw IllegalStateException("Cache is dirty.")
        }
        wallpapers!!.filter { it.isSelected }.forEach { return it }

        throw IllegalStateException("Cache is dirty.")
    }

    override fun selectWallpaper(wallpaperId: String) {
        if (isDirty()) {
            throw IllegalStateException("Cache is dirty.")
        }
        for (wallpaper in wallpapers!!) {
            wallpaper.isSelected = TextUtils.equals(wallpaperId, wallpaper.wallpaperId)
        }
    }

    override fun getWallpapers(): List<AdvanceWallpaperEntity> {
        if (isDirty()) {
            throw IllegalStateException("Cache is dirty.")
        }
        return ArrayList(wallpapers!!)
    }

    override fun evictAll() {
        wallpapers = null
    }

    override fun isCached(wallpaperId: String): Boolean {
        if (isDirty()) {
            return false
        }
        return wallpapers!!.any { TextUtils.equals(wallpaperId, it.wallpaperId) }
    }

    override fun isDirty(): Boolean {
        return wallpapers == null || wallpapers!!.isEmpty()
    }

    override fun makeDirty() {
        evictAll()
    }

}