package com.androidkits.camera.mediaselect;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.androidkits.camera.R;
import com.androidkits.camera.mediaselect.adapter.FolderAdapter;
import com.androidkits.camera.mediaselect.adapter.MediaInfoAdapter;
import com.androidkits.camera.mediaselect.config.ImageSelectorConfig;
import com.androidkits.camera.mediaselect.entry.Folder;
import com.androidkits.camera.mediaselect.entry.MediaInfoModel;
import com.common.library.bean.camera.MediaInfo;
import com.common.library.commons.common.CommonActivity;
import com.common.library.commons.constants.ArouterPath;
import com.common.library.commons.constants.Constants;
import com.common.library.utils.DateUtils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.core.os.EnvironmentCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

/**
 * Created by xiaodong.jin on 2019/05/27.
 * 功能描述：相册图片选择器
 */
@Route(path = ArouterPath.camera.AlbumSelectorActivity)
public class AlbumSelectorActivity extends CommonActivity {
    private static final int PERMISSION_WRITE_EXTERNAL_REQUEST_CODE = 0x00000011;
    private static final int PERMISSION_CAMERA_REQUEST_CODE = 0x00000012;
    private static final int CAMERA_REQUEST_CODE = 0x00000010;

    private RecyclerView mRvImage;
    private TextView mTvTime;
    private View mMasking;
    private RecyclerView mRvFolder;
    private RelativeLayout mRlTopBar;
    private FrameLayout mBtnBack;
    private LinearLayout mBtnFolder;
    private TextView mTvFolderName;
    private ImageView mIvArrow;
    private FrameLayout mBtnConfirm;
    private TextView mTvConfirm;
    private LinearLayout mRlBottomBar;
    private FrameLayout mBtnPreview;
    private TextView mTvPreview;
    private TextView mTvSize;

    /**
     * 选择器配置
     */
    private ImageSelectorConfig imageSelectorConfig;
    /**
     * 布局管理器
     */
    private GridLayoutManager mLayoutManager;

    private MediaInfoAdapter mAdapter;

    private ArrayList<Folder> mFolders;
    private Folder mFolder;

    /**
     * 文件夹是否初始化
     */
    private boolean isInitFolder;

    private boolean isOpenFolder;


    @Override
    protected int getHeaderLayoutId() {
        return CUSTOMER_HEADER;
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.camera_activity_album_selector;
    }

    @Override
    protected void initValue(Bundle onSavedInstance) {
        super.initValue(onSavedInstance);
        imageSelectorConfig = (ImageSelectorConfig) getIntent().getSerializableExtra(Constants.camera.CONFIG_EXTRA);
        if (imageSelectorConfig == null) {
            imageSelectorConfig = new ImageSelectorConfig.Builder().build();
        }

    }

    @Override
    protected void initWidget(Bundle onSavedInstance) {
        super.initWidget(onSavedInstance);
        mRvImage = findViewById(R.id.rv_image);
        mTvTime = findViewById(R.id.tv_time);
        mMasking = findViewById(R.id.masking);
        mRvFolder = findViewById(R.id.rv_folder);
        mRlTopBar = findViewById(R.id.rl_top_bar);
        mBtnBack = findViewById(R.id.btn_back);
        mBtnFolder = findViewById(R.id.btn_folder);
        mTvFolderName = findViewById(R.id.tv_folder_name);
        mIvArrow = findViewById(R.id.iv_arrow);
        mBtnConfirm = findViewById(R.id.btn_confirm);
        mTvConfirm = findViewById(R.id.tv_confirm);
        mRlBottomBar = findViewById(R.id.rl_bottom_bar);
        mBtnPreview = findViewById(R.id.btn_preview);
        mTvPreview = findViewById(R.id.tv_preview);
        mTvSize = findViewById(R.id.tv_size);


    }

    @Override
    protected void initListener(Bundle onSavedInstance) {
        super.initListener(onSavedInstance);

        mBtnBack.setOnClickListener(v -> finish());
        mBtnPreview.setOnClickListener(v -> jumpToPreviewActivity());
        mBtnConfirm.setOnClickListener(v -> confrim());
        mBtnFolder.setOnClickListener(v -> toggleFolder());
        mMasking.setOnClickListener(v -> closeFolder());

        mRvImage.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                changeTime();
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                changeTime();
            }
        });
    }


    @Override
    protected void initData() {
        super.initData();

        initImageList();
        checkPermissionAndLoadImages();
        hideFolderList();
        setSelectImageCount(mAdapter.getSelectImages());

    }

    /**
     * 隐藏文件夹列表
     */
    private void hideFolderList() {
        mRvFolder.post(() -> {
            mRvFolder.setTranslationY(mRvFolder.getHeight());
            mRvFolder.setVisibility(View.GONE);
        });
    }

    /**
     * 初始化图片列表
     */
    private void initImageList() {
        // 判断屏幕方向
        Configuration configuration = getResources().getConfiguration();
        if (configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            mLayoutManager = new GridLayoutManager(this, 4);
        } else {
            mLayoutManager = new GridLayoutManager(this, 6);
        }
        mRvImage.setLayoutManager(mLayoutManager);
        mAdapter = new MediaInfoAdapter(this, imageSelectorConfig.mMaxCount, imageSelectorConfig.isSingle, imageSelectorConfig.isSingleVideo);
        mRvImage.setAdapter(mAdapter);

        if (imageSelectorConfig.mSelectedImages != null && mAdapter != null) {
            mAdapter.setSelectedImages(imageSelectorConfig.mSelectedImages);
            imageSelectorConfig.mSelectedImages = null;
        }


        ((SimpleItemAnimator) mRvImage.getItemAnimator()).setSupportsChangeAnimations(false);
        if (mFolders != null && !mFolders.isEmpty()) {
            setFolder(mFolders.get(0));
        }
        mAdapter.setOnImageSelectListener((selectImage, isSelect) -> setSelectImageCount(selectImage));
        mAdapter.setOnItemClickListener(new MediaInfoAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(MediaInfo image, int position) {
                toPreviewActivity(mAdapter.getData(), position);
            }

            @Override
            public void OnCameraClick() {
                checkPermissionAndCamera();
            }
        });
    }


    /**
     * 检查权限并加载SD卡里的图片。
     */
    private void checkPermissionAndLoadImages() {
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
//            Toast.makeText(this, "没有图片", Toast.LENGTH_LONG).show();
            return;
        }
        int hasWriteExternalPermission = ContextCompat.checkSelfPermission(getApplication(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (hasWriteExternalPermission == PackageManager.PERMISSION_GRANTED) {
            //有权限，加载图片。
            loadImageForSDCard();
        } else {
            //没有权限，申请权限。
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_WRITE_EXTERNAL_REQUEST_CODE);
        }
    }

    /**
     * 从sd卡 加载图片
     */
    private void loadImageForSDCard() {
        MediaInfoModel.loadImageForSDCard(this, imageSelectorConfig.isContainsVideo, folders -> {
            mFolders = folders;
            runOnUiThread(() -> {
                if (mFolders != null && !mFolders.isEmpty()) {
                    initFolderList();
                    mFolders.get(0).setUseCamera(imageSelectorConfig.useCamera);
                    setFolder(mFolders.get(0));
                }
            });
        });


    }

    /**
     * 初始化文件夹列表
     */
    private void initFolderList() {
        if (mFolders != null && !mFolders.isEmpty()) {
            isInitFolder = true;
            mRvFolder.setLayoutManager(new LinearLayoutManager(AlbumSelectorActivity.this));
            FolderAdapter adapter = new FolderAdapter(AlbumSelectorActivity.this, mFolders);
            adapter.setOnFolderSelectListener(folder -> {
                setFolder(folder);
                closeFolder();
            });
            mRvFolder.setAdapter(adapter);
        }
    }


    /**
     * 检查相机权限并启动
     */
    private void checkPermissionAndCamera() {
        int hasCameraPermission = ContextCompat.checkSelfPermission(getApplication(),
                Manifest.permission.CAMERA);
        if (hasCameraPermission == PackageManager.PERMISSION_GRANTED) {
            //有调起相机拍照。
            openCamera();
        } else {
            //没有权限，申请权限。
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA}, PERMISSION_CAMERA_REQUEST_CODE);
        }
    }

    /**
     * 打开相机
     */
    private void openCamera() {
        Intent captureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (captureIntent.resolveActivity(getPackageManager()) != null) {
            File photoFile = createImageFile();
            if (photoFile != null) {
//                mPhotoPath = photoFile.getAbsolutePath();
                //通过FileProvider创建一个content类型的Uri
                Uri photoUri = FileProvider.getUriForFile(this, getPackageName() + ".fileprovider", photoFile);
                captureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                captureIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                startActivityForResult(captureIntent, CAMERA_REQUEST_CODE);
            }
        }
    }

    private File createImageFile() {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String imageFileName = String.format("JPEG_%s.jpg", timeStamp);
        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        if (!storageDir.exists()) {
             storageDir.mkdir();
        }
        File tempFile = new File(storageDir, imageFileName);
        if (!Environment.MEDIA_MOUNTED.equals(EnvironmentCompat.getStorageState(tempFile))) {
            return null;
        }
        return tempFile;
    }

    private void setSelectImageCount(List<MediaInfo> selectImageList) {
        int count = selectImageList == null ? 0 : selectImageList.size();
        if (count == 0) {
            mTvConfirm.setText("确定");
            mTvPreview.setText("预览");
        } else {
            mTvPreview.setText("预览(" + count + ")");
            if (imageSelectorConfig.isSingle) {
                mTvConfirm.setText("确定");
            } else if (imageSelectorConfig.mMaxCount > 0) {
                mTvConfirm.setText("确定(" + count + "/" + imageSelectorConfig.mMaxCount + ")");
            } else {
                mTvConfirm.setText("确定(" + count + ")");
            }
        }
        long countSize = 0;
        for (MediaInfo mediaInfo : selectImageList) {
            countSize = countSize + mediaInfo.size;
        }
        mTvSize.setText("原图(" + DateUtils.getNetFileSizeDescription(countSize) + ")");
    }


    /**
     * 跳转到预览界面
     */
    private void jumpToPreviewActivity() {

    }

    private void toPreviewActivity(ArrayList<MediaInfo> data, int position) {

    }

    /**
     * 确认按钮
     */
    private void confrim() {

    }


    private void toggleFolder() {
        if (isInitFolder) {
            if (isOpenFolder) {
                closeFolder();
            } else {
                openFolder();
            }
        }

    }

    /**
     * 打开文件夹列表
     */
    private void openFolder() {
        if (!isOpenFolder) {
            mIvArrow.setImageResource(R.drawable.camera_icon_arrowup);
            mMasking.setVisibility(View.VISIBLE);
            ObjectAnimator animator = ObjectAnimator.ofFloat(mRvFolder, "translationY",
                    -mRvFolder.getHeight(), 30, -30, 10, -10, 0).setDuration(300);
            animator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationStart(Animator animation) {
                    super.onAnimationStart(animation);
                    mRvFolder.setVisibility(View.VISIBLE);
                }
            });
            animator.start();
            isOpenFolder = true;
        }
    }


    /**
     * 收起文件夹列表
     */
    private void closeFolder() {
        if (isOpenFolder) {
            mIvArrow.setImageResource(R.drawable.camera_icon_arrowdown);
            mMasking.setVisibility(View.GONE);
            ObjectAnimator animator = ObjectAnimator.ofFloat(mIvArrow, "translationY",
                    0, 30, -30, -mIvArrow.getHeight()).setDuration(300);
            animator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    mIvArrow.setVisibility(View.GONE);
                }
            });
            animator.start();
            isOpenFolder = false;
        }
    }

    /**
     * 改变时间条显示的时间（显示图片列表中的第一个可见图片的时间）
     */
    private void changeTime() {
    }

    /**
     * 设置选中的文件夹，同时刷新图片列表
     *
     * @param folder
     */
    private void setFolder(Folder folder) {
        if (folder != null && mAdapter != null && !folder.equals(mFolder)) {
            mFolder = folder;
            mTvFolderName.setText(folder.getName());
            mRvImage.scrollToPosition(0);
            mAdapter.refresh(folder.getImages(), folder.isUseCamera());
        }
    }
}
