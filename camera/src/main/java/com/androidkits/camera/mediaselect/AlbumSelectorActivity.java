package com.androidkits.camera.mediaselect;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.provider.Settings;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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
import com.common.library.file.FileDir;
import com.common.library.file.FileUtils;
import com.common.library.utils.DateUtils;
import com.common.library.utils.ToastUtils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
    //    private RelativeLayout mRlTopBar;
    private FrameLayout mBtnBack;
    private LinearLayout mBtnFolder;
    private TextView mTvFolderName;
    private ImageView mIvArrow;
    private FrameLayout mBtnConfirm;
    private TextView mTvConfirm;
    //    private LinearLayout mRlBottomBar;
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
    private String mPhotoPath;
    private boolean applyLoadImage = false;
    private boolean isShowTime;


    private Handler mHideHandler = new Handler();

    private Runnable mHide = this::hideTime;

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
//        mRlTopBar = findViewById(R.id.rl_top_bar);
        mBtnBack = findViewById(R.id.btn_back);
        mBtnFolder = findViewById(R.id.btn_folder);
        mTvFolderName = findViewById(R.id.tv_folder_name);
        mIvArrow = findViewById(R.id.iv_arrow);
        mBtnConfirm = findViewById(R.id.btn_confirm);
        mTvConfirm = findViewById(R.id.tv_confirm);
//        mRlBottomBar = findViewById(R.id.rl_bottom_bar);
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

    @Override
    protected void onStart() {
        super.onStart();
        if (applyLoadImage) {
            applyLoadImage = false;
            checkPermissionAndLoadImages();
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (mLayoutManager != null && mAdapter != null) {
            //切换为竖屏
            if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
                mLayoutManager.setSpanCount(3);
            }
            //切换为横屏
            else if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                mLayoutManager.setSpanCount(5);
            }
            mAdapter.notifyDataSetChanged();
        }
    }

    /**
     * 数据返回处理
     *
     * @param requestCode 请求码
     * @param resultCode  返回码
     * @param data        返回数据
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ImageSelectorConfig.RESULT_CODE) {
            //如果是图片预览返回结果
            if (data != null && data.getBooleanExtra(ImageSelectorConfig.IS_CONFIRM, false)) {
                //如果用户在预览页点击了确定，就直接把用户选中的图片返回给用户。
                confrim();
            } else {
                mAdapter.notifyDataSetChanged();
                setSelectImageCount(mAdapter.getSelectImages());
            }
        } else if (requestCode == CAMERA_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                ArrayList<MediaInfo> selectImages = new ArrayList<>();
                selectImages.add(new MediaInfo(mPhotoPath, 0, null, null, 0));
                setResult(selectImages);
                finish();
            }
        }
    }


    @Override
    public void onBackPressed() {
        if (isOpenFolder) {
            closeFolder();
            return;
        }
        super.onBackPressed();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_WRITE_EXTERNAL_REQUEST_CODE) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //允许权限，加载图片。
                loadImageForSDCard();
            } else {
                //拒绝权限，弹出提示框。
                showExceptionDialog(true);
            }
        } else if (requestCode == PERMISSION_CAMERA_REQUEST_CODE) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //允许权限，有调起相机拍照。
                openCamera();
            } else {
                //拒绝权限，弹出提示框。
                showExceptionDialog(false);
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mHideHandler != null && mHide != null) {
            mHideHandler.removeCallbacks(mHide);
        }
    }

    private void setResult(ArrayList<MediaInfo> images) {
        Intent intent = new Intent();
        intent.putParcelableArrayListExtra(Constants.camera.PHOTO_SELECT_RESULT, images);
        setResult(RESULT_OK, intent);
    }

    /**
     * 发生没有权限等异常时，显示一个提示dialog.
     */
    private void showExceptionDialog(final boolean applyLoad) {
        new AlertDialog.Builder(this)
                .setCancelable(false)
                .setTitle("提示")
                .setMessage("该相册需要赋予访问存储和拍照的权限，请到“设置”>“应用”>“权限”中配置权限。")
                .setNegativeButton("取消", (dialog, which) -> {
                    dialog.cancel();
                    finish();
                }).setPositiveButton("确定", (dialog, which) -> {
            dialog.cancel();
            startAppSettings();
            if (applyLoad) {
                applyLoadImage = true;
            }
        }).show();
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


        if (mRvImage.getItemAnimator() != null) {
            ((SimpleItemAnimator) mRvImage.getItemAnimator()).setSupportsChangeAnimations(false);
        }
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
                mPhotoPath = photoFile.getAbsolutePath();
                //通过FileProvider创建一个content类型的Uri
                Uri photoUri = FileProvider.getUriForFile(this, getPackageName() + ".fileprovider", photoFile);
                captureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                captureIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                startActivityForResult(captureIntent, CAMERA_REQUEST_CODE);
            }
        }
    }

    private File createImageFile() {
        File storageDir = FileUtils.getDirectory(FileDir.FilesPath.FILE_IMG);
        if (storageDir == null || !FileUtils.checkSDCard()) {
            return null;
        }
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String imageFileName = String.format("JPEG_%s.jpg", timeStamp);

//        File storageDir = Environment.getExternalStoragePublicDirectory(
//                Environment.DIRECTORY_PICTURES);
//        if (!storageDir.exists()) {
//            storageDir.mkdir();
//        }
        File tempFile = new File(storageDir, imageFileName);
        if (!Environment.MEDIA_MOUNTED.equals(EnvironmentCompat.getStorageState(tempFile))) {
            return null;
        }
        return tempFile;
    }

    private void setSelectImageCount(List<MediaInfo> selectImageList) {
        int count = selectImageList == null ? 0 : selectImageList.size();
        if (count == 0) {
            mTvConfirm.setText(getString(R.string.camera_confirm));
            mTvPreview.setText(getString(R.string.camera_preview));
        } else {
            mTvPreview.setText(String.format(getString(R.string.camera_previce_with_count), count));
            if (imageSelectorConfig.isSingle) {
                mTvConfirm.setText(getString(R.string.camera_confirm));
            } else if (imageSelectorConfig.mMaxCount > 0) {
                mTvConfirm.setText(String.format(getString(R.string.camera_confirm_with_count), count, imageSelectorConfig.mMaxCount));
            } else {
                mTvConfirm.setText(String.format(getString(R.string.camera_confirm_count), count));
            }
        }
        long countSize = 0;

        if (selectImageList != null) {
            for (MediaInfo mediaInfo : selectImageList) {
                countSize = countSize + mediaInfo.size;
            }
        }
        mTvSize.setText(String.format(getString(R.string.camera_photo_size), DateUtils.getNetFileSizeDescription(countSize)));
    }


    /**
     * 跳转到预览界面
     */
    private void jumpToPreviewActivity() {
        ArrayList<MediaInfo> images = new ArrayList<>(mAdapter.getSelectImages());
        toPreviewActivity(images, 0);
    }

    private void toPreviewActivity(ArrayList<MediaInfo> images, int position) {
        if (images != null && !images.isEmpty()) {
            ToastUtils.show("跳转到预览界面");
//            PreviewActivity.openActivity(this, images,
//                    mAdapter.getSelectImages(), isSingle, mMaxCount, position, isSingleVideo);
        }
    }

    /**
     * 确认按钮
     */
    private void confrim() {
        if (mAdapter == null) {
            return;
        }
        //因为图片的实体类是Image，而我们返回的是String数组，所以要进行转换。
        ArrayList<MediaInfo> selectImages = mAdapter.getSelectImages();
        if (selectImages == null || selectImages.size() == 0) {
            Toast.makeText(this, "请选择", Toast.LENGTH_SHORT).show();
            return;
        }
        //点击确定，把选中的图片通过Intent传给上一个Activity。
        setResult(selectImages);
        finish();
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
            ObjectAnimator animator = ObjectAnimator.ofFloat(mRvFolder, "translationY",
                    0, 30, -30, -mRvFolder.getHeight()).setDuration(300);
            animator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    mRvFolder.setVisibility(View.GONE);
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
        int firstVisibleItem = getFirstVisibleItem();
        MediaInfo image = mAdapter.getFirstVisibleImage(firstVisibleItem);
        if (image != null) {
            String time = DateUtils.getImageTime(image.getTime() * 1000);
            mTvTime.setText(time);
            showTime();
            mHideHandler.removeCallbacks(mHide);
            mHideHandler.postDelayed(mHide, 1500);
        }
    }

    private int getFirstVisibleItem() {
        return mLayoutManager.findFirstVisibleItemPosition();
    }

    /**
     * 显示时间条
     */
    private void showTime() {
        if (!isShowTime) {
            ObjectAnimator.ofFloat(mTvTime, "alpha", 0, 1).setDuration(300).start();
            isShowTime = true;
        }
    }

    /**
     * 隐藏时间条
     */
    private void hideTime() {
        if (isShowTime) {
            ObjectAnimator.ofFloat(mTvTime, "alpha", 1, 0).setDuration(300).start();
            isShowTime = false;
        }
    }

    /**
     * 设置选中的文件夹，同时刷新图片列表
     *
     * @param folder 文件夹
     */
    private void setFolder(Folder folder) {
        if (folder != null && mAdapter != null && !folder.equals(mFolder)) {
            mFolder = folder;
            mTvFolderName.setText(folder.getName());
            mRvImage.scrollToPosition(0);
            mAdapter.refresh(folder.getImages(), folder.isUseCamera());
        }
    }


    /**
     * 启动应用的设置
     */
    private void startAppSettings() {
        Intent intent = new Intent(
                Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse("package:" + getPackageName()));
        startActivity(intent);
    }
}
