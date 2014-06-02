package wholepicache;

import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.Map;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

public class AsyncImageLoader extends AsyncTask<Object, Void, Bitmap> {

	private static Map<String, SoftReference<Bitmap>> imageCache = new HashMap<String, SoftReference<Bitmap>>();

	private static final String TAG = "AsyncImageLoader";
	ImageView view;

	public AsyncImageLoader(ImageView image) {
		view = image;
		// TODO Auto-generated constructor stub
	}

	public static Bitmap getImageChcae(String url) {

		Bitmap bitmap = null;

		if (imageCache.containsKey(url)) {

			SoftReference<Bitmap> softReference = imageCache.get(url);

			if (softReference.get() != null) {
				
				Log.v(TAG, "   loadDrawable from cache");
				
				bitmap = softReference.get();

			}
		}
		
		return bitmap;

	}

	@Override
	protected void onPostExecute(Bitmap result) {
		view.setImageBitmap(result);
	}

	@Override
	protected Bitmap doInBackground(Object... params) {
		// TODO Auto-generated method stub
		try{
			Bitmap bitmap;
			String imageUrl=(String) params[0];
			if(imageUrl==null||imageUrl.lastIndexOf("/")==-1){
				
				Log.d(TAG, "   illegal url: " + imageUrl);
				return null;
			}
			//内存caahe
			if(imageCache.containsKey(imageUrl)){
				
				SoftReference<Bitmap> softReference=imageCache.get(imageUrl);
				
				if(softReference.get()!=null){
					
					Log.v(TAG, "   loadDrawable from cache");
					
					return softReference.get();
				}
			}
			//本地文件
			//网络加载
			
			
		}catch(Exception ex){
			
			ex.printStackTrace();
		}
		return null;
	}

}
