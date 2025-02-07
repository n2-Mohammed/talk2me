package Talk2Me.spnllc.ng;

import android.Manifest;
import android.animation.*;
import android.app.*;
import android.content.*;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.*;
import android.graphics.*;
import android.graphics.Typeface;
import android.graphics.drawable.*;
import android.media.*;
import android.net.*;
import android.net.Uri;
import android.os.*;
import android.text.*;
import android.text.style.*;
import android.util.*;
import android.view.*;
import android.view.View;
import android.view.View.*;
import android.view.animation.*;
import android.webkit.*;
import android.widget.*;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.annotation.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import java.io.*;
import java.io.File;
import java.text.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.*;
import org.json.*;

public class StatusCreateActivity extends AppCompatActivity {
	
	private Timer _timer = new Timer();
	private FirebaseDatabase _firebase = FirebaseDatabase.getInstance();
	private FirebaseStorage _firebase_storage = FirebaseStorage.getInstance();
	
	private FloatingActionButton _fab;
	private double n = 0;
	private String colorCode = "";
	private double n2 = 0;
	private boolean cancel = false;
	private String statusaPath = "";
	private String statusName = "";
	private String statusPath = "";
	private String push = "";
	private HashMap<String, Object> map2 = new HashMap<>();
	private String pics = "";
	private String nam = "";
	private String verified = "";
	
	private LinearLayout linear2;
	private LinearLayout linear_sts;
	private LinearLayout linear_tools;
	private LinearLayout linear1;
	private EditText edittext1;
	private ImageView im_change_font;
	private ImageView im_change_color;
	
	private TimerTask time;
	private Calendar c = Calendar.getInstance();
	private StorageReference status_pic = _firebase_storage.getReference("status_pic");
	private OnCompleteListener<Uri> _status_pic_upload_success_listener;
	private OnSuccessListener<FileDownloadTask.TaskSnapshot> _status_pic_download_success_listener;
	private OnSuccessListener _status_pic_delete_success_listener;
	private OnProgressListener _status_pic_upload_progress_listener;
	private OnProgressListener _status_pic_download_progress_listener;
	private OnFailureListener _status_pic_failure_listener;
	
	private DatabaseReference storys = _firebase.getReference("storys");
	private ChildEventListener _storys_child_listener;
	private Intent in = new Intent();
	private FirebaseAuth auth;
	private OnCompleteListener<AuthResult> _auth_create_user_listener;
	private OnCompleteListener<AuthResult> _auth_sign_in_listener;
	private OnCompleteListener<Void> _auth_reset_password_listener;
	private OnCompleteListener<Void> auth_updateEmailListener;
	private OnCompleteListener<Void> auth_updatePasswordListener;
	private OnCompleteListener<Void> auth_emailVerificationSentListener;
	private OnCompleteListener<Void> auth_deleteUserListener;
	private OnCompleteListener<Void> auth_updateProfileListener;
	private OnCompleteListener<AuthResult> auth_phoneAuthListener;
	private OnCompleteListener<AuthResult> auth_googleSignInListener;
	
	private DatabaseReference guest = _firebase.getReference("guest");
	private ChildEventListener _guest_child_listener;
	
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.status_create);
		initialize(_savedInstanceState);
		FirebaseApp.initializeApp(this);
		
		if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED
		|| ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
			ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1000);
		} else {
			initializeLogic();
		}
	}
	
	@Override
	public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		if (requestCode == 1000) {
			initializeLogic();
		}
	}
	
	private void initialize(Bundle _savedInstanceState) {
		_fab = findViewById(R.id._fab);
		
		linear2 = findViewById(R.id.linear2);
		linear_sts = findViewById(R.id.linear_sts);
		linear_tools = findViewById(R.id.linear_tools);
		linear1 = findViewById(R.id.linear1);
		edittext1 = findViewById(R.id.edittext1);
		im_change_font = findViewById(R.id.im_change_font);
		im_change_color = findViewById(R.id.im_change_color);
		auth = FirebaseAuth.getInstance();
		
		im_change_font.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				_pickRandomFont();
			}
		});
		
		im_change_color.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				_pickRandomColor();
			}
		});
		
		_fab.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				c = Calendar.getInstance();
				_fabRotation(true);
				statusPath = FileUtil.getExternalStorageDir().concat("/talk2me/status/".concat(String.valueOf((long)(c.getTimeInMillis())).concat(".png")));
				_Save(linear1, statusPath);
				statusName = Uri.parse(statusPath).getLastPathSegment();
				if (FileUtil.isExistFile(statusPath)) {
					status_pic.child(statusName).putFile(Uri.fromFile(new File(statusPath))).addOnFailureListener(_status_pic_failure_listener).addOnProgressListener(_status_pic_upload_progress_listener).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
						@Override
						public Task<Uri> then(Task<UploadTask.TaskSnapshot> task) throws Exception {
							return status_pic.child(statusName).getDownloadUrl();
						}}).addOnCompleteListener(_status_pic_upload_success_listener);
				}
			}
		});
		
		_status_pic_upload_progress_listener = new OnProgressListener<UploadTask.TaskSnapshot>() {
			@Override
			public void onProgress(UploadTask.TaskSnapshot _param1) {
				double _progressValue = (100.0 * _param1.getBytesTransferred()) / _param1.getTotalByteCount();
				
			}
		};
		
		_status_pic_download_progress_listener = new OnProgressListener<FileDownloadTask.TaskSnapshot>() {
			@Override
			public void onProgress(FileDownloadTask.TaskSnapshot _param1) {
				double _progressValue = (100.0 * _param1.getBytesTransferred()) / _param1.getTotalByteCount();
				
			}
		};
		
		_status_pic_upload_success_listener = new OnCompleteListener<Uri>() {
			@Override
			public void onComplete(Task<Uri> _param1) {
				final String _downloadUrl = _param1.getResult().toString();
				_fabRotation(false);
				c = Calendar.getInstance();
				push = storys.push().getKey();
				map2 = new HashMap<>();
				map2.put("uid", FirebaseAuth.getInstance().getCurrentUser().getUid());
				map2.put("image", _downloadUrl);
				map2.put("time", String.valueOf((long)(c.getTimeInMillis())));
				map2.put("type", "status");
				map2.put("nam", nam);
				map2.put("pics", pics);
				map2.put("push key", push);
				map2.put("verified", verified);
				map2.put("color", colorCode);
				storys.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).updateChildren(map2);
				map2.clear();
				SketchwareUtil.showMessage(getApplicationContext(), "Added!");
				in.setClass(getApplicationContext(), HomeActivity.class);
				startActivity(in);
				finish();
			}
		};
		
		_status_pic_download_success_listener = new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
			@Override
			public void onSuccess(FileDownloadTask.TaskSnapshot _param1) {
				final long _totalByteCount = _param1.getTotalByteCount();
				
			}
		};
		
		_status_pic_delete_success_listener = new OnSuccessListener() {
			@Override
			public void onSuccess(Object _param1) {
				
			}
		};
		
		_status_pic_failure_listener = new OnFailureListener() {
			@Override
			public void onFailure(Exception _param1) {
				final String _message = _param1.getMessage();
				_fabRotation(false);
				SketchwareUtil.showMessage(getApplicationContext(), _message);
			}
		};
		
		_storys_child_listener = new ChildEventListener() {
			@Override
			public void onChildAdded(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				
			}
			
			@Override
			public void onChildChanged(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				
			}
			
			@Override
			public void onChildMoved(DataSnapshot _param1, String _param2) {
				
			}
			
			@Override
			public void onChildRemoved(DataSnapshot _param1) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				
			}
			
			@Override
			public void onCancelled(DatabaseError _param1) {
				final int _errorCode = _param1.getCode();
				final String _errorMessage = _param1.getMessage();
				
			}
		};
		storys.addChildEventListener(_storys_child_listener);
		
		_guest_child_listener = new ChildEventListener() {
			@Override
			public void onChildAdded(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				pics = _childValue.get("avatar").toString();
				nam = _childValue.get("display_name").toString();
				verified = _childValue.get("verified").toString();
			}
			
			@Override
			public void onChildChanged(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				
			}
			
			@Override
			public void onChildMoved(DataSnapshot _param1, String _param2) {
				
			}
			
			@Override
			public void onChildRemoved(DataSnapshot _param1) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				
			}
			
			@Override
			public void onCancelled(DatabaseError _param1) {
				final int _errorCode = _param1.getCode();
				final String _errorMessage = _param1.getMessage();
				
			}
		};
		guest.addChildEventListener(_guest_child_listener);
		
		auth_updateEmailListener = new OnCompleteListener<Void>() {
			@Override
			public void onComplete(Task<Void> _param1) {
				final boolean _success = _param1.isSuccessful();
				final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : "";
				
			}
		};
		
		auth_updatePasswordListener = new OnCompleteListener<Void>() {
			@Override
			public void onComplete(Task<Void> _param1) {
				final boolean _success = _param1.isSuccessful();
				final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : "";
				
			}
		};
		
		auth_emailVerificationSentListener = new OnCompleteListener<Void>() {
			@Override
			public void onComplete(Task<Void> _param1) {
				final boolean _success = _param1.isSuccessful();
				final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : "";
				
			}
		};
		
		auth_deleteUserListener = new OnCompleteListener<Void>() {
			@Override
			public void onComplete(Task<Void> _param1) {
				final boolean _success = _param1.isSuccessful();
				final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : "";
				
			}
		};
		
		auth_phoneAuthListener = new OnCompleteListener<AuthResult>() {
			@Override
			public void onComplete(Task<AuthResult> task) {
				final boolean _success = task.isSuccessful();
				final String _errorMessage = task.getException() != null ? task.getException().getMessage() : "";
				
			}
		};
		
		auth_updateProfileListener = new OnCompleteListener<Void>() {
			@Override
			public void onComplete(Task<Void> _param1) {
				final boolean _success = _param1.isSuccessful();
				final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : "";
				
			}
		};
		
		auth_googleSignInListener = new OnCompleteListener<AuthResult>() {
			@Override
			public void onComplete(Task<AuthResult> task) {
				final boolean _success = task.isSuccessful();
				final String _errorMessage = task.getException() != null ? task.getException().getMessage() : "";
				
			}
		};
		
		_auth_create_user_listener = new OnCompleteListener<AuthResult>() {
			@Override
			public void onComplete(Task<AuthResult> _param1) {
				final boolean _success = _param1.isSuccessful();
				final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : "";
				
			}
		};
		
		_auth_sign_in_listener = new OnCompleteListener<AuthResult>() {
			@Override
			public void onComplete(Task<AuthResult> _param1) {
				final boolean _success = _param1.isSuccessful();
				final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : "";
				
			}
		};
		
		_auth_reset_password_listener = new OnCompleteListener<Void>() {
			@Override
			public void onComplete(Task<Void> _param1) {
				final boolean _success = _param1.isSuccessful();
				
			}
		};
	}
	
	private void initializeLogic() {
		_fab.setCompatElevation(4f);
		_circleRipple("#EEEEEE", im_change_font);
		_circleRipple("#EEEEEE", im_change_color);
		_transparentStatusBar();
		_pickRandomColor();
		FileUtil.makeDir(FileUtil.getExternalStorageDir().concat("/talk2me/status/"));
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
		overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
		_LengthOfEditText(edittext1, 60);
	}
	
	public void _circleRipple(final String _color, final View _v) {
		android.content.res.ColorStateList clrb = new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{Color.parseColor(_color)});
		android.graphics.drawable.RippleDrawable ripdrb = new android.graphics.drawable.RippleDrawable(clrb , null, null);
		_v.setBackground(ripdrb);
	}
	
	
	public void _transparentStatusBar() {
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
	}
	
	
	public void _removeScollBar(final View _view) {
		_view.setVerticalScrollBarEnabled(false); _view.setHorizontalScrollBarEnabled(false);
	}
	
	
	public void _pickRandomColor() {
		n = SketchwareUtil.getRandom((int)(0), (int)(8));
		if (n == 0) {
			colorCode = "#65A9E0";
			linear2.setBackgroundColor(0xFF65A9E0);
			linear1.setBackgroundColor(0xFF65A9E0);
		}
		if (n == 1) {
			colorCode = "#E56555";
			linear2.setBackgroundColor(0xFFE56555);
			linear1.setBackgroundColor(0xFFE56555);
		}
		if (n == 2) {
			colorCode = "#5FBED5";
			linear2.setBackgroundColor(0xFF5FBED5);
			linear1.setBackgroundColor(0xFF5FBED5);
		}
		if (n == 3) {
			colorCode = "#F2739A";
			linear2.setBackgroundColor(0xFFF2739A);
			linear1.setBackgroundColor(0xFFF2739A);
		}
		if (n == 4) {
			colorCode = "#76C84C";
			linear2.setBackgroundColor(0xFF76C84C);
			linear1.setBackgroundColor(0xFF76C84C);
		}
		if (n == 5) {
			colorCode = "#8D84EE";
			linear2.setBackgroundColor(0xFF8D84EE);
			linear1.setBackgroundColor(0xFF8D84EE);
		}
		if (n == 6) {
			colorCode = "#50A6E6";
			linear1.setBackgroundColor(0xFF50A6E6);
			linear2.setBackgroundColor(0xFF50A6E6);
		}
		if (n == 7) {
			colorCode = "#F28C48";
			linear2.setBackgroundColor(0xFFF28C48);
			linear1.setBackgroundColor(0xFFF28C48);
		}
		if (n == 8) {
			colorCode = "#009688";
			linear2.setBackgroundColor(0xFF009688);
			linear1.setBackgroundColor(0xFF009688);
		}
	}
	
	
	public void _pickRandomFont() {
		n2 = SketchwareUtil.getRandom((int)(0), (int)(4));
		if (n2 == 0) {
			edittext1.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/medium.ttf"), 0);
		}
		if (n2 == 1) {
			edittext1.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/regular.ttf"), 0);
		}
		if (n2 == 2) {
			edittext1.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/bryndan_write.ttf"), 0);
		}
		if (n2 == 3) {
			edittext1.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/norican_regular.ttf"), 0);
		}
		if (n2 == 4) {
			edittext1.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/oswald_heavy.ttf"), 0);
		}
	}
	
	
	public void _fabRotation(final boolean _start) {
		if (_start) {
			_fab.setEnabled(false);
			cancel = false;
			_fab.setImageResource(R.drawable.loading_100);
			time = new TimerTask() {
				@Override
				public void run() {
					runOnUiThread(new Runnable() {
						@Override
						public void run() {
							if (!cancel) {
								_fab.setRotation((float)(_fab.getRotation() + 10));
							}
						}
					});
				}
			};
			_timer.scheduleAtFixedRate(time, (int)(0), (int)(20));
		}
		else {
			_fab.setImageResource(R.drawable.ic_arrow_forward_white);
			time.cancel();
			cancel = true;
			_fab.setRotation((float)(0));
			_fab.setEnabled(true);
		}
	}
	
	
	public void _Save(final View _view, final String _filepath) {
		Bitmap returnedBitmap = Bitmap.createBitmap(_view.getWidth(), _view.getHeight(),Bitmap.Config.ARGB_8888);
		
		Canvas canvas = new Canvas(returnedBitmap);
		android.graphics.drawable.Drawable bgDrawable =_view.getBackground();
		if (bgDrawable!=null) {
			bgDrawable.draw(canvas);
		} else {
			canvas.drawColor(Color.WHITE);
		}
		_view.draw(canvas);
		
		java.io.File file = new java.io.File(_filepath);
		if (file == null) {
			showMessage("Error creating media file, check storage permissions: ");
			return; }
		try {
			java.io.FileOutputStream fos = new java.io.FileOutputStream(file); returnedBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
			fos.close();
		} catch (java.io.FileNotFoundException e) {
			showMessage("File not found: " + e.getMessage()); } catch (java.io.IOException e) {
			showMessage("Error accessing file: " + e.getMessage());
			
		}
		Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE); intent.setData(Uri.fromFile(file)); sendBroadcast(intent);
	}
	
	
	public void _LengthOfEditText(final TextView _editText, final double _value_character) {
		InputFilter[] gb = _editText.getFilters(); InputFilter[] newFilters = new InputFilter[gb.length + 1]; System.arraycopy(gb, 0, newFilters, 0, gb.length); newFilters[gb.length] = new InputFilter.LengthFilter((int)_value_character); _editText.setFilters(newFilters);
	}
	
	
	@Deprecated
	public void showMessage(String _s) {
		Toast.makeText(getApplicationContext(), _s, Toast.LENGTH_SHORT).show();
	}
	
	@Deprecated
	public int getLocationX(View _v) {
		int _location[] = new int[2];
		_v.getLocationInWindow(_location);
		return _location[0];
	}
	
	@Deprecated
	public int getLocationY(View _v) {
		int _location[] = new int[2];
		_v.getLocationInWindow(_location);
		return _location[1];
	}
	
	@Deprecated
	public int getRandom(int _min, int _max) {
		Random random = new Random();
		return random.nextInt(_max - _min + 1) + _min;
	}
	
	@Deprecated
	public ArrayList<Double> getCheckedItemPositionsToArray(ListView _list) {
		ArrayList<Double> _result = new ArrayList<Double>();
		SparseBooleanArray _arr = _list.getCheckedItemPositions();
		for (int _iIdx = 0; _iIdx < _arr.size(); _iIdx++) {
			if (_arr.valueAt(_iIdx))
			_result.add((double)_arr.keyAt(_iIdx));
		}
		return _result;
	}
	
	@Deprecated
	public float getDip(int _input) {
		return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, _input, getResources().getDisplayMetrics());
	}
	
	@Deprecated
	public int getDisplayWidthPixels() {
		return getResources().getDisplayMetrics().widthPixels;
	}
	
	@Deprecated
	public int getDisplayHeightPixels() {
		return getResources().getDisplayMetrics().heightPixels;
	}
}