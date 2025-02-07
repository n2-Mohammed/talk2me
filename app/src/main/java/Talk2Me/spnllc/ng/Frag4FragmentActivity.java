package Talk2Me.spnllc.ng;

import android.animation.*;
import android.app.*;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.*;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.annotation.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
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
import de.hdodenhof.circleimageview.*;
import java.io.*;
import java.text.*;
import java.util.*;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.*;
import org.json.*;

public class Frag4FragmentActivity extends Fragment {
	
	private Timer _timer = new Timer();
	private FirebaseDatabase _firebase = FirebaseDatabase.getInstance();
	
	private String fontName = "";
	private String typeace = "";
	private HashMap<String, Object> h = new HashMap<>();
	
	private ScrollView vscroll1;
	private LinearLayout linear_main;
	private LinearLayout linear2;
	private LinearLayout l_edit_name;
	private LinearLayout linear7;
	private LinearLayout l_edit_bio;
	private LinearLayout linear8;
	private LinearLayout linear9;
	private LinearLayout linear11;
	private LinearLayout linear_word;
	private CircleImageView circleimageview1;
	private ImageView imageview1;
	private LinearLayout linear3;
	private ImageView im_edit_name;
	private TextView textview1;
	private LinearLayout linear12;
	private TextView tx_username;
	private ImageView im_verified;
	private ImageView imageview3;
	private LinearLayout linear6;
	private ImageView im_edit_bio;
	private TextView textview3;
	private TextView tx_bio;
	private ImageView imageview5;
	private LinearLayout linear10;
	private TextView textview5;
	private TextView tx_number;
	
	private TimerTask t;
	private Intent in = new Intent();
	private AlertDialog.Builder d;
	private SharedPreferences pic;
	private SharedPreferences nam;
	private SharedPreferences boo;
	private SharedPreferences nums;
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
	private SharedPreferences verify;
	
	@NonNull
	@Override
	public View onCreateView(@NonNull LayoutInflater _inflater, @Nullable ViewGroup _container, @Nullable Bundle _savedInstanceState) {
		View _view = _inflater.inflate(R.layout.frag4_fragment, _container, false);
		initialize(_savedInstanceState, _view);
		FirebaseApp.initializeApp(getContext());
		initializeLogic();
		return _view;
	}
	
	private void initialize(Bundle _savedInstanceState, View _view) {
		vscroll1 = _view.findViewById(R.id.vscroll1);
		linear_main = _view.findViewById(R.id.linear_main);
		linear2 = _view.findViewById(R.id.linear2);
		l_edit_name = _view.findViewById(R.id.l_edit_name);
		linear7 = _view.findViewById(R.id.linear7);
		l_edit_bio = _view.findViewById(R.id.l_edit_bio);
		linear8 = _view.findViewById(R.id.linear8);
		linear9 = _view.findViewById(R.id.linear9);
		linear11 = _view.findViewById(R.id.linear11);
		linear_word = _view.findViewById(R.id.linear_word);
		circleimageview1 = _view.findViewById(R.id.circleimageview1);
		imageview1 = _view.findViewById(R.id.imageview1);
		linear3 = _view.findViewById(R.id.linear3);
		im_edit_name = _view.findViewById(R.id.im_edit_name);
		textview1 = _view.findViewById(R.id.textview1);
		linear12 = _view.findViewById(R.id.linear12);
		tx_username = _view.findViewById(R.id.tx_username);
		im_verified = _view.findViewById(R.id.im_verified);
		imageview3 = _view.findViewById(R.id.imageview3);
		linear6 = _view.findViewById(R.id.linear6);
		im_edit_bio = _view.findViewById(R.id.im_edit_bio);
		textview3 = _view.findViewById(R.id.textview3);
		tx_bio = _view.findViewById(R.id.tx_bio);
		imageview5 = _view.findViewById(R.id.imageview5);
		linear10 = _view.findViewById(R.id.linear10);
		textview5 = _view.findViewById(R.id.textview5);
		tx_number = _view.findViewById(R.id.tx_number);
		d = new AlertDialog.Builder(getActivity());
		pic = getContext().getSharedPreferences("pic", Activity.MODE_PRIVATE);
		nam = getContext().getSharedPreferences("nam", Activity.MODE_PRIVATE);
		boo = getContext().getSharedPreferences("boo", Activity.MODE_PRIVATE);
		nums = getContext().getSharedPreferences("nums", Activity.MODE_PRIVATE);
		auth = FirebaseAuth.getInstance();
		verify = getContext().getSharedPreferences("verify", Activity.MODE_PRIVATE);
		
		im_edit_name.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				
			}
		});
		
		im_edit_bio.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				
			}
		});
		
		_guest_child_listener = new ChildEventListener() {
			@Override
			public void onChildAdded(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				pic.edit().putString("photo", _childValue.get("avatar").toString()).commit();
				nam.edit().putString("me", _childValue.get("display_name").toString()).commit();
				boo.edit().putString("info", _childValue.get("bio").toString()).commit();
				verify.edit().putString("verified", _childValue.get("verified").toString()).commit();
				nums.edit().putString("phone", _childValue.get("number").toString()).commit();
				_offline();
			}
			
			@Override
			public void onChildChanged(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				pic.edit().putString("photo", _childValue.get("avatar").toString()).commit();
				nam.edit().putString("me", _childValue.get("display_name").toString()).commit();
				boo.edit().putString("info", _childValue.get("bio").toString()).commit();
				nums.edit().putString("phone", _childValue.get("number").toString()).commit();
				_offline();
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
		_removeScollBar(vscroll1);
		_uix();
		tx_username.setTypeface(Typeface.createFromAsset(getContext().getAssets(),"fonts/regular.ttf"), 0);
		tx_bio.setTypeface(Typeface.createFromAsset(getContext().getAssets(),"fonts/regular.ttf"), 0);
		tx_number.setTypeface(Typeface.createFromAsset(getContext().getAssets(),"fonts/regular.ttf"), 0);
		textview1.setTypeface(Typeface.createFromAsset(getContext().getAssets(),"fonts/regular.ttf"), 1);
		textview3.setTypeface(Typeface.createFromAsset(getContext().getAssets(),"fonts/regular.ttf"), 1);
		textview5.setTypeface(Typeface.createFromAsset(getContext().getAssets(),"fonts/regular.ttf"), 1);
		imageview3.setColorFilter(0xFF212121, PorterDuff.Mode.MULTIPLY);
		imageview1.setColorFilter(0xFF212121, PorterDuff.Mode.MULTIPLY);
		imageview5.setColorFilter(0xFF212121, PorterDuff.Mode.MULTIPLY);
		im_edit_name.setColorFilter(0xFF212121, PorterDuff.Mode.MULTIPLY);
		im_edit_bio.setColorFilter(0xFF212121, PorterDuff.Mode.MULTIPLY);
		_offline();
	}
	
	public void _removeScollBar(final View _view) {
		_view.setVerticalScrollBarEnabled(false); _view.setHorizontalScrollBarEnabled(false);
	}
	
	
	public void _Send(final Intent _IntentName, final String _to, final String _subject, final String _text) {
		_IntentName.setAction(Intent.ACTION_VIEW);
		_IntentName.setData(Uri.parse("mailto:kelvin@zoneone.com.sg".concat(_to)));
		_IntentName.putExtra("android.intent.extra.SUBJECT", _subject);
		_IntentName.putExtra("android.intent.extra.TEXT", _text);
		startActivity(_IntentName);
	}
	
	
	public void _uix() {
		imageview3.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)360, (int)1, 0xFF90A4AE, 0xFFECEFF1));
		imageview5.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)360, (int)1, 0xFF90A4AE, 0xFFECEFF1));
		imageview1.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)360, (int)1, 0xFF90A4AE, 0xFFECEFF1));
		_circleRipple("#e0e0e0", im_edit_name);
		_circleRipple("#e0e0e0", im_edit_bio);
	}
	
	
	public void _offline() {
		tx_username.setText(nam.getString("me", ""));
		tx_bio.setText(boo.getString("info", ""));
		tx_number.setText(nums.getString("phone", ""));
		Glide.with(getContext().getApplicationContext()).load(Uri.parse(pic.getString("photo", ""))).into(circleimageview1);
	}
	
	
	public void _circleRipple(final String _color, final View _v) {
		android.content.res.ColorStateList clrb = new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{Color.parseColor(_color)});
		android.graphics.drawable.RippleDrawable ripdrb = new android.graphics.drawable.RippleDrawable(clrb , null, null);
		_v.setBackground(ripdrb);
	}
	
}