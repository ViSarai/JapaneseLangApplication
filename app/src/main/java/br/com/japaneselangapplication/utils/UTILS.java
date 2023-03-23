package br.com.japaneselangapplication.utils;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.exifinterface.media.ExifInterface;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.CustomTarget;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.com.japaneselangapplication.R;


public class UTILS {

    private static final String TAG = "UTILS";

    //APP
    private static boolean isAppRunning = false;
    private static boolean isUserLoggedIn = false;

    public static final int FLIP_VERTICAL = 1;
    public static final int FLIP_HORIZONTAL = 2;

    public static Context context;

    private static String id;

    //There is no knowledge that is not power
    public static synchronized void DebugLog(String TAG, Object o) {
        Log.d(TAG, String.valueOf(o));
    }

    public static void clearId() {
        id = "";
    }

    public static void setUserLoggedIn(boolean isLoggedIn) {
        isUserLoggedIn = isLoggedIn;
    }

    public static boolean isUserLoggedIn() {
        return isUserLoggedIn;
    }

    public static void setAppRunning(boolean isRunning) {
        isAppRunning = isRunning;
    }

    public static boolean isAppRunning() {
        return isAppRunning;
    }


    public interface DialogListener {
        void onDismiss();
    }



    public static void showFullScreen(Window window) {
        final int UI_OPTIONS = View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;

        window.getDecorView().setSystemUiVisibility(UI_OPTIONS);
    }

    public static void showPopupDialog(Context context, int resource, String titulo, String msg, DialogListener listener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(false);
        AlertDialog alert = builder.create();
        alert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        View view = LayoutInflater.from(context).inflate(R.layout.dialog_popup, null);

        TextView tvTitulo = view.findViewById(R.id.titulo);
        TextView tvMsg = view.findViewById(R.id.msg);
        CardView btnOk = view.findViewById(R.id.btn_ok);
        ImageView icon = view.findViewById(R.id.icon);

        if (resource == -1) {
            icon.setVisibility(View.GONE);
            if(titulo.equals("")){
                tvTitulo.setVisibility(View.GONE);
            }
            tvMsg.setTextSize(24);
        }else if (resource != 0) {
            icon.setImageResource(resource);
        }

        if (titulo != null) {
            tvTitulo.setText(titulo);
        }

        if (msg != null) {
            tvMsg.setText(msg);
        }

        btnOk.setOnClickListener(view2 -> {
            try{alert.dismiss();}catch (Exception e){e.printStackTrace();}
            if (listener != null)listener.onDismiss();
        });

        alert.setView(view);

        try{alert.show();}catch (Exception e){e.printStackTrace();}
    }

    public static void getImageAt(Context activity, String link, ImageView imageView) {
        try {
            Glide.with(activity)
                    .load(link)
                    .into(imageView);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void getGifAt(Context activity, String link, ImageView imageView) {
        try {
            Glide.with(activity)
                    .load(link)
                    .into(imageView);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
//
//    public static void openCustomTab(Context context, String url) {
//
//        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
//
//        CustomTabColorSchemeParams.Builder schemeParams = new CustomTabColorSchemeParams.Builder();
//        schemeParams.setToolbarColor(ContextCompat.getColor(context, R.color.PrimaryBackground));
//        builder.setDefaultColorSchemeParams(schemeParams.build());
//        builder.setShareState(CustomTabsIntent.SHARE_STATE_ON);
//        builder.setUrlBarHidingEnabled(false);
//        builder.setCloseButtonIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_arrow_left_custom_tabs));
//
//        CustomTabsIntent customTabsIntent = builder.build();
//
//        customTabsIntent.intent.setPackage("com.android.chrome");
//        customTabsIntent.intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//
//        try {
//            customTabsIntent.launchUrl(context, Uri.parse(url));
//        } catch (Exception e) {
//            e.printStackTrace();
//            customTabsIntent.intent.setPackage(null);
//            customTabsIntent.launchUrl(context, Uri.parse(url));
//        }
//    }
//
//    public static Bitmap tintImage(Bitmap bitmap, int color) {
//        Paint paint = new Paint();
//        paint.setColorFilter(new PorterDuffColorFilter(color, PorterDuff.Mode.SRC_IN));
//        Bitmap bitmapResult = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
//        Canvas canvas = new Canvas(bitmapResult);
//        canvas.drawBitmap(bitmap, 0, 0, paint);
//        return bitmapResult;
//    }
//
//    @SuppressLint("HardwareIds")
//    public static String generateSecureID() {
//        return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
//    }
//
//    public static void checkBadgeResponse(Activity activity, JSONObject response) {
//
//        if (response.has("requestRating")) {
//            try {
//                activity.startActivity(new Intent(activity, RatingActivity.class));
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//
//        if (response.has("newBadges")) {
//            try {
//                String badges = response.getString("newBadges");
//                Intent intent = new Intent(activity, WinBadgeActivity.class);
//                intent.putExtra("badges", badges);
//                activity.startActivity(intent);
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//        }
//
///*        if (response.has("anagog_keys")) {
//            try {
//                Map<String, Integer> result = new ObjectMapper().readValue(response.getJSONObject("anagog_keys").toString(), HashMap.class);
//                Map<String, Stats.Type> stats = new HashMap<>();
//
//                for ( String key : result.keySet() ) {
//                    stats.put(key, Stats.Type.USER_DEFINED_INTEGER);
//                }
//
//                JEMAWrapper.INSTANCE.setupUserDefinedStats(stats);
//                JEMAWrapper.INSTANCE.addCustomMS(result);
//
//            } catch (JSONException | JsonProcessingException ex) {
//                ex.printStackTrace();
//            }
//        }*/
//    }
//
//    public static Bitmap generateQrcode(String str) throws WriterException {
//        QRCodeWriter writer = new QRCodeWriter();
//        BitMatrix bitMatrix = writer.encode(str, BarcodeFormat.QR_CODE, 400, 400);
//
//        int w = bitMatrix.getWidth();
//        int h = bitMatrix.getHeight();
//        int[] pixels = new int[w * h];
//        for (int y = 0; y < h; y++) {
//            for (int x = 0; x < w; x++) {
//                pixels[y * w + x] = bitMatrix.get(x, y) ? Color.BLACK : Color.WHITE;
//            }
//        }
//
//        Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
//        bitmap.setPixels(pixels, 0, w, 0, 0, w, h);
//        return bitmap;
//    }
//
//    public static String PrettyTime(Context activity, String data, String format){
//
//        if (data != null) {
//            PrettyTime prettyTime = new PrettyTime(activity.getResources().getConfiguration().locale);
//
//            @SuppressLint("SimpleDateFormat")
//            SimpleDateFormat dateFormatData = new SimpleDateFormat(format);
//
//            Date date = null;
//            try {
//                date = dateFormatData.parse(data);
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }
//            return prettyTime.format(date).toLowerCase().replaceAll("ago", "");}
//        else {
//            return "";
//        }
//    }
//
//    public static String convertDoubleToRealFormat(double valor, boolean removeSymbol) {
//        try {
//            BigDecimal modelVal = new BigDecimal(valor);
//            BigDecimal displayVal = modelVal.setScale(2, RoundingMode.HALF_EVEN);
//            Locale locale = new Locale("pt", "BR");
//            NumberFormat usdCostFormat = NumberFormat.getCurrencyInstance(locale);
//            usdCostFormat.setMinimumFractionDigits(2);
//            usdCostFormat.setMaximumFractionDigits(2);
//
//            if (removeSymbol) {
//                return usdCostFormat.format(displayVal.doubleValue()).replace("\u00a0", "").replace("R$", "");
//            } else {
//                return usdCostFormat.format(displayVal.doubleValue()).replace("\u00a0", "").replace("R$", "R$ ");
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            if (!removeSymbol) {
//                return "R$ " + valor;
//            } else {
//                return valor + "";
//            }
//        }
//    }
//
//    public static boolean isAppInstalled(String packageName, PackageManager packageManager) {
//        try {
//            packageManager.getPackageInfo(packageName, 0);
//            return true;
//        } catch (PackageManager.NameNotFoundException e) {
//            return false;
//        }
//    }
//
//    public static BigDecimal parseToBigDecimal(String value, Locale locale) {
//        String replaceable = String.format("[%s,.\\s]", NumberFormat.getCurrencyInstance(locale).getCurrency().getSymbol());
//
//        String cleanString = value.replaceAll(replaceable, "");
//
//        return new BigDecimal(cleanString).setScale(
//                2, BigDecimal.ROUND_FLOOR).divide(new BigDecimal(100), BigDecimal.ROUND_FLOOR
//        );
//    }
//
//    public static int calculateAge(String dn) throws ParseException {
//        Calendar today = Calendar.getInstance();
//        SimpleDateFormat sdf;
//
//        if (dn.contains("/")) {
//            sdf = new SimpleDateFormat("dd/MM/yyyy");
//        } else {
//            sdf = new SimpleDateFormat("yyyy-MM-dd");
//        }
//        Calendar dob = Calendar.getInstance();
//        dob.setTime(sdf.parse(dn));
//
//        int curYear = today.get(Calendar.YEAR);
//        int dobYear = dob.get(Calendar.YEAR);
//
//        int age = curYear - dobYear;
//
//
//        int curMonth = today.get(Calendar.MONTH);
//        int dobMonth = dob.get(Calendar.MONTH);
//        if (dobMonth > curMonth) {
//            age--;
//        } else if (dobMonth == curMonth) {
//            int curDay = today.get(Calendar.DAY_OF_MONTH);
//            int dobDay = dob.get(Calendar.DAY_OF_MONTH);
//            if (dobDay > curDay) {
//                age--;
//            }
//        }
//
//        return age;
//    }
//
//
//    public static String dataConvert(String data, Context context) {
//
//        String[] meses = context.getResources().getStringArray(R.array.meses);
//
//        String dia = data.substring(0, 2);
//        String mes = data.substring(3, 5);
//        int mesValeu = Integer.parseInt(mes);
//
//        mesValeu--;
//
//        mes = meses[mesValeu];
//
//        return dia + " " + "de" + " " + mes;
//    }
//
//    public static String dataConvertDDMMYYYY(String data, Context context) {
//
//        String[] meses = context.getResources().getStringArray(R.array.meses);
//
//        String dia = data.substring(0, 2);
//        String mes = data.substring(3, 5);
//        int mesValeu = Integer.parseInt(mes);
//
//        mesValeu--;
//
//        mes = meses[mesValeu];
//
//        return dia + " " + "de" + " " + mes;
//    }
//
//    public static String getActualDate(String formatOutput) {
//
//        DateFormat df = new SimpleDateFormat(formatOutput);
//        Date currentDate = Calendar.getInstance().getTime();
//
//        return df.format(currentDate);
//    }
//
//    public static String convertDateFormat(Context context, String formatInput, String formatOutput, String data, boolean returnToday) {
//
//        try {
//            if (returnToday) {
//                Date calendarActualDate = Calendar.getInstance().getTime();
//
//                SimpleDateFormat dfc = new SimpleDateFormat("dd/MM/yyyy");
//                SimpleDateFormat dfi = new SimpleDateFormat(formatInput);
//
//                String strActualFormated = dfc.format(calendarActualDate);
//
//                Date dateInput = dfi.parse(data);
//                String dateOutput = dfc.format(dateInput);
//
//                Date actualDate = dfc.parse(strActualFormated);
//                Date dateFormated = dfc.parse(dateOutput);
//
//                if (actualDate.equals(dateFormated)) {
//                    return context.getResources().getString(R.string.hoje);
//                }else {
//                    SimpleDateFormat formatter = new SimpleDateFormat(formatInput);
//                    Date date = formatter.parse(data);
//                    DateFormat df = new SimpleDateFormat(formatOutput);
//                    return df.format(date);
//                }
//            }else {
//                SimpleDateFormat formatter = new SimpleDateFormat(formatInput);
//                Date date = formatter.parse(data);
//                DateFormat df = new SimpleDateFormat(formatOutput);
//                return df.format(date);
//            }
//        } catch (ParseException e) {
//            e.printStackTrace();
//            return "";
//        }
//    }
//
//    public static long convertDateStringToMilli(String data) {
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
//        Date date = null;
//        try {
//            date = sdf.parse(data);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//
//        try{
//            return date.getTime();
//        }catch (Exception e){
//            return 0;
//        }
//    }
//
//    public static String camelCase(String stringToConvert) {
//        if (TextUtils.isEmpty(stringToConvert)) {
//            return "";
//        }
//        return Character.toUpperCase(stringToConvert.charAt(0)) +
//                stringToConvert.substring(1).toLowerCase();
//    }
//
//    //Converte um valor float em valor monetário
//    public static String convertMonetaryValue(float valor, String linguagem, String pais) {
//
//        Locale locale = new Locale(linguagem, pais);
//        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(locale);
//
//        return currencyFormatter.format(valor);
//    }
//
//    public static String dateZuluToGMT(String date) {
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault());
//        dateFormat.setTimeZone(TimeZone.getDefault());
//        try {
//            return getGMTHour(dateFormat.parse(date).toString());
//        } catch (ParseException e) {
//            e.printStackTrace();
//            return date;
//        }
//    }
//
//    public static String getGMTHour(String date) {
//        String[] chunks = date.split(" ");
//        if (!chunks[3].isEmpty()) {
//            return chunks[3];
//        } else {
//            return date;
//        }
//    }
//
//    public static String generateLevel(UserModel user, Context context) {
//        String txtNivel;
//
//        if (user.getSexo() != null && user.getSexo().startsWith("F")) {
//            txtNivel = user.isSocioTorcedor() ? context.getResources().getString(R.string.nivel_socio_f) : context.getResources().getString(R.string.torcedora);
//        } else {
//            txtNivel = user.isSocioTorcedor() ? context.getResources().getString(R.string.nivel_socio) : context.getResources().getString(R.string.torcedor);
//        }
//
//        //Premium
//        if (!txtNivel.equals(context.getResources().getString(R.string.torcedor)) && !txtNivel.equals(context.getResources().getString(R.string.torcedora))) {
//            if (user.getSexo() != null && user.getSexo().startsWith("F")) {
//                txtNivel = user.isPremium() ? txtNivel + " + " + context.getResources().getString(R.string.nivel_std_f) : txtNivel;
//            } else {
//                txtNivel = user.isPremium() ? txtNivel + " + " + context.getResources().getString(R.string.nivel_std) : txtNivel;
//            }
//        } else {
//            if (user.getSexo() != null && user.getSexo().startsWith("F")) {
//                txtNivel = user.isPremium() ? context.getResources().getString(R.string.nivel_std_f) : txtNivel;
//            } else {
//                txtNivel = user.isPremium() ? context.getResources().getString(R.string.nivel_std) : txtNivel;
//            }
//        }
//
//        return txtNivel;
//    }
//
//    public static String loadJSONFromAsset(Activity activity, String nameFile) {
//        String json = null;
//        try {
//            InputStream is = activity.getAssets().open(nameFile);
//            int size = is.available();
//            byte[] buffer = new byte[size];
//            is.read(buffer);
//            is.close();
//            json = new String(buffer, "UTF-8");
//        } catch (IOException ex) {
//            ex.printStackTrace();
//            return null;
//        }
//        return json;
//    }
//
//    public static String isToday(Context context, String date) {
//        String dataFinal = "";
//        try {
//            Calendar cal1 = Calendar.getInstance();
//            String data = date.split(" ")[0];
//
//            Calendar cAmanha = Calendar.getInstance();
//            cAmanha.add(Calendar.DAY_OF_MONTH, +1);
//
//            @SuppressLint("SimpleDateFormat") DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
//
//            String d1 = df.format(cal1.getTime());
//            String amanha = df.format(cAmanha.getTime());
//
//            if (data.equals(d1)) {
//                dataFinal = context.getResources().getString(R.string.hoje);
//            } else if (data.equals(amanha)) {
//                dataFinal = context.getResources().getString(R.string.amanha);
//            } else {
//                dataFinal = data.substring(0, 5);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        return dataFinal;
//    }
//
//    public static Hashtable<String, Object> SocioToHash(SocioData data) {
//        Hashtable<String, Object> params = new Hashtable<>();
//        if (data.getBairro() != null && !data.getBairro().isEmpty())
//            params.put("bairro", data.getBairro());
//
//        if (data.getUf() != null && !data.getUf().isEmpty())
//            params.put("uf", data.getUf());
//
//        if (data.getCidade() != null && !data.getCidade().isEmpty())
//            params.put("cidade", data.getCidade());
//
//        if (data.getNome() != null && !data.getNome().isEmpty())
//            params.put("nome", data.getNome());
//
//        if (data.getCidade() != null && !data.getCidade().isEmpty())
//            params.put("cidade", data.getCidade());
//
//        if (data.getCep() != null && !data.getCep().isEmpty())
//            params.put("cep", data.getCep());
//
//        if (data.getNumero() != null && !data.getNumero().isEmpty())
//            params.put("numero", data.getNumero());
//
//        if (data.getNome() != null && !data.getNome().isEmpty())
//            params.put("nome", data.getNome());
//
//        if (data.getCpf() != null && !data.getCpf().isEmpty())
//            params.put("cpf", data.getCpf());
//
//        if (data.getEndereco() != null && !data.getEndereco().isEmpty())
//            params.put("endereco", data.getEndereco());
//
//        if (data.getId() != 0)
//            params.put("idpessoa", data.getId());
//
//        if (data.getStatus() != null && !data.getStatus().isEmpty())
//            params.put("status", data.getStatus());
//
//        if (data.getIsActive() != null)
//            params.put("isActive", data.getIsActive());
//
//        return params;
//    }
//
//    public static boolean canHaveCard(UserModel user) {
//        if (user.getDn() == null || user.getDn().isEmpty()) {
//            return false;
//        }
//
//        if (user.getNome() == null || user.getNome().isEmpty()) {
//            return false;
//        }
//
//        return user.getCelular() != null && !user.getCelular().isEmpty();
//    }
//
//    public static boolean checkSocioIsActive(String status) {
//        switch (status.toUpperCase()) {
//            case "ATIVO":
//            case "QUITADA":
//            case "ATRASADO":
//            case "ADIMPLENTE":
//                return true;
//            case "CANCELADA":
//            case "VENCIDA":
//            case "EXPIRADA":
//            case "EXTORNADA":
//            case "RENOVADA":
//            case "NOVA_ADESAO":
//            case "INADIMPLENTE":
//            case "A_INICIAR":
//                return false;
//        }
//        return false;
//    }
//
//    public static boolean checkSocioBeneficioIsActive(String status) {
//        switch (status.toUpperCase()) {
//            case "ATIVO":
//            case "QUITADA":
//            case "ATRASADO":
//            case "ADIMPLENTE":
//                return true;
//            case "CANCELADA":
//            case "VENCIDA":
//            case "EXPIRADA":
//            case "EXTORNADA":
//            case "RENOVADA":
//            case "NOVA_ADESAO":
//            case "INADIMPLENTE":
//            case "A_INICIAR":
//                return false;
//        }
//        return false;
//    }
//
//    public static String getFormattedDate(String date) {
//        try {
//            String[] datetime = date.split(" ");
//            String _date;
//            String[] dt = datetime[0].split("-");
//            _date = dt[2] + "/" + dt[1] + "/" + dt[0];
//            String time;
//            String[] tm = datetime[1].split(":");
//            time = tm[0] + ":" + tm[1];
//
//            date = _date + " - " + time;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return date;
//    }
//
//    public static String getPath(Context context, Uri uri) {
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            // DocumentProvider
//            if (DocumentsContract.isDocumentUri(context, uri)) {
//                // ExternalStorageProvider
//                if (isExternalStorageDocument(uri)) {
//                    final String docId = DocumentsContract.getDocumentId(uri);
//                    final String[] split = docId.split(":");
//
//                    return Environment.getExternalStorageDirectory() + "/" + split[1];
//                    // TODO handle non-primary volumes
//                }
//                // DownloadsProvider
//                else if (isDownloadsDocument(uri)) {
//                    final String id = DocumentsContract.getDocumentId(uri);
//                    final Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.parseLong(id));
//                    return getDataColumn(context, contentUri, null, null);
//                }
//                // MediaProvider
//                else if (isMediaDocument(uri)) {
//                    final String docId = DocumentsContract.getDocumentId(uri);
//                    final String[] split = docId.split(":");
//                    final String type = split[0];
//                    Uri contentUri = null;
//                    if ("image".equals(type)) {
//                        contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
//                    } else if ("video".equals(type)) {
//                        contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
//                    } else if ("audio".equals(type)) {
//                        contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
//                    }
//                    final String selection = "_id=?";
//                    final String[] selectionArgs = new String[]{split[1]};
//                    return getDataColumn(context, contentUri, selection, selectionArgs);
//                }
//            }
//        }
//        // MediaStore (and general)
//        else if ("content".equalsIgnoreCase(uri.getScheme())) {
//            // Return the remote address
//            if (isGooglePhotosUri(uri))
//                return uri.getLastPathSegment();
//            return getDataColumn(context, uri, null, null);
//        }
//        // File
//        else if ("file".equalsIgnoreCase(uri.getScheme())) {
//            return uri.getPath();
//        }
//        return null;
//    }
//
//    public static String getRealPathFromURI(final Context context, final Uri uri) {
//
//        if (Objects.equals(uri.getScheme(), "file")) {
//            return uri.toString();
//
//        } else if (Objects.equals(uri.getScheme(), "content")) {
//            // DocumentProvider
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                if (DocumentsContract.isDocumentUri(context, uri)) {
//
//                    // ExternalStorageProvider
//                    if (isExternalStorageDocument(uri)) {
//                        final String docId = DocumentsContract.getDocumentId(uri);
//                        final String[] split = docId.split(":");
//
//                        return Environment.getExternalStorageDirectory() + "/" + split[1];
//
//                        // to_do handle non-primary volumes
//                    }
//                    // DownloadsProvider
//                    else if (isDownloadsDocument(uri)) {
//
//                        String id = DocumentsContract.getDocumentId(uri);
//
//                        long idl = -1L;
//                        try {
//                            idl = Long.parseLong(id);
//                        } catch (Exception e) {
//                            UTILS.DebugLog(TAG, e);
//                        }
//
//                        if (idl == -1) {
//                            String prefix = "raw:";
//                            if (id.startsWith(prefix)) {
//                                id = id.substring(prefix.length());
//                            }
//                            return id;
//                        }
//
//                        final Uri contentUri = ContentUris.withAppendedId(
//                                Uri.parse("content://downloads/public_downloads"), Long.parseLong(id));
//
//
//                        return getDataColumn(context, contentUri, null, null);
//                    }
//                    // MediaProvider
//                    else if (isMediaDocument(uri)) {
//                        final String docId = DocumentsContract.getDocumentId(uri);
//                        final String[] split = docId.split(":");
//                        final String type = split[0];
//
//                        Uri contentUri = null;
//                        if ("image".equals(type)) {
//                            contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
//                        } else if ("video".equals(type)) {
//                            contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
//                        } else if ("audio".equals(type)) {
//                            contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
//                        }
//
//                        final String selection = "_id=?";
//                        final String[] selectionArgs = new String[]{
//                                split[1]
//                        };
//
//                        return getDataColumn(context, contentUri, selection, selectionArgs);
//                    }
//                }
//                // MediaStore (and general)
//                else if ("content".equalsIgnoreCase(uri.getScheme())) {
//
//                    // Return the remote address
//                    if (isGooglePhotosUri(uri)) {
//                        return uri.getLastPathSegment();
//                    }
//
//                    return getDataColumn(context, uri, null, null);
//                }
//                // File
//                else if ("file".equalsIgnoreCase(uri.getScheme())) {
//                    return uri.getPath();
//                }
//            }
//            // MediaStore (and general)
//            else if ("content".equalsIgnoreCase(uri.getScheme())) {
//                return getDataColumn(context, uri, null, null);
//            }
//            // File
//            else if ("file".equalsIgnoreCase(uri.getScheme())) {
//                return uri.getPath();
//            }
//        }
//
//        return null;
//    }
//
//
//    public static String getDataColumn(Context context, Uri uri, String selection,
//                                       String[] selectionArgs) {
//
//        Cursor cursor = null;
//        final String column = "_data";
//        final String[] projection = {
//                column
//        };
//
//        try {
//            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
//                    null);
//            if (cursor != null && cursor.moveToFirst()) {
//                final int column_index = cursor.getColumnIndexOrThrow(column);
//                return cursor.getString(column_index);
//            }
//        } finally {
//            if (cursor != null)
//                cursor.close();
//        }
//        return null;
//    }
//
//    public static boolean isGooglePhotosUri(Uri uri) {
//        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
//    }
//
//    public static boolean isExternalStorageDocument(Uri uri) {
//        return "com.android.externalstorage.documents".equals(uri.getAuthority());
//    }
//
//    public static boolean isDownloadsDocument(Uri uri) {
//        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
//    }
//
//    public static boolean isMediaDocument(Uri uri) {
//        return "com.android.providers.media.documents".equals(uri.getAuthority());
//    }
//
//    public static String formatHourFromDBEvent(String dt) {
//        boolean success = false;
//        String formatted = "";
//
//        if (dt.contains(" ")) {
//            String[] hourDt = dt.split(" ");
//            if (hourDt[1].contains(":")) {
//                String[] hhmmss = hourDt[1].split(":");
//                if (hhmmss.length == 3) {
//                    formatted = hhmmss[0] + ":" + hhmmss[1];
//                } else {
//                    formatted = hourDt[1];
//                }
//                success = true;
//            }
//        }
//
//        return (success ? formatted : dt);
//    }
//
//    public static String formatDateFromDB(String dt) {
//        boolean success = false;
//        String formatted = "";
//
//        try {
//            if (dt.contains(" ")) {
//                String[] hourDt = dt.split(" ");
//
//                String justDate = hourDt[0];
//                String[] unformatted = justDate.split("-");
//                if (unformatted.length >= 3) {
//                    formatted = unformatted[2] + "/" +
//                            unformatted[1] + "/" +
//                            unformatted[0];
//
//                    success = true;
//                }
//            } else {
//                String[] unformatted = dt.split("-");
//                if (unformatted.length >= 3) {
//                    formatted = unformatted[2] + "/" +
//                            unformatted[1] + "/" +
//                            unformatted[0];
//
//                    success = true;
//                }
//            }
//
//            return (success ? formatted : dt);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return "";
//        }
//    }
//
//    public static String getValor(float valor) {
//        String result = "R$ @";
//
//        NumberFormat format = NumberFormat.getCurrencyInstance(Locale.US);
//        String currency = format.format(valor);
//        currency = currency.replace("$", "");
//        result = result.replace("@", currency);
//        return result;
//    }
//
//    public static boolean hasImage(String url) {
//        url = url.toUpperCase();
//
//        return ((url.endsWith(".PNG")) ||
//                (url.endsWith(".JPG")) ||
//                (url.endsWith(".JPEG")) ||
//                (url.endsWith(".GIF")) ||
//                (url.endsWith(".BMP")) ||
//                (url.endsWith(".PSD")) ||
//                (url.endsWith(".ICO")) ||
//                (url.endsWith(".TGA")) ||
//                (url.endsWith(".TIF")) ||
//                (url.endsWith(".TIFF")) ||
//                (url.endsWith(".JIF")) ||
//                (url.endsWith(".JFIF")) ||
//                (url.endsWith(".JP2")) ||
//                (url.endsWith(".JPX")) ||
//                (url.endsWith(".J2K")) ||
//                (url.endsWith(".J2C")) ||
//                (url.endsWith(".FPX")) ||
//                (url.endsWith(".PCD")) ||
//                (url.endsWith(".EXIF")) ||
//                (url.endsWith(".BPG")) ||
//                (url.endsWith(".PPM")) ||
//                (url.endsWith(".PGM")) ||
//                (url.endsWith(".PBM")) ||
//                (url.endsWith(".PNM")));
//    }
//
//    public static String formatDateFromDBEvent(String dt) {
//        boolean success = false;
//        String formatted = "";
//
//        if (dt.contains(" ")) {
//            String[] hourDt = dt.split(" ");
//
//            String justDate = hourDt[0];
//            String[] unformatted = justDate.split("-");
//            if (unformatted.length >= 3) {
//                formatted = unformatted[2] + "." +
//                        unformatted[1] + "." +
//                        unformatted[0];
//
//                success = true;
//            }
//        } else {
//            String[] unformatted = dt.split("-");
//            if (unformatted.length >= 3) {
//                formatted = unformatted[2] + "." +
//                        unformatted[1] + "." +
//                        unformatted[0];
//
//                success = true;
//            }
//        }
//
//        return (success ? formatted : dt);
//    }
//
//    public static int parseColorByString(String colorString) {
//
//        if (!colorString.startsWith("#")) {
//            colorString = "#" + colorString;
//        }
//
//        return Color.parseColor(colorString);
//    }
//
//    public static List<String> getStringsBetweenStrings(String text, String pattern1, String pattern2) {
//
//        List<String> list = new ArrayList<>();
//
//        Pattern p = Pattern.compile(Pattern.quote(pattern1) + "(.*?)" + Pattern.quote(pattern2));
//        Matcher m = p.matcher(text);
//        while (m.find()) {
//            list.add(m.group(1));
//        }
//
//        return list;
//    }
//
//    public static String formatBirthDate(String birthDate) {
//        boolean success = false;
//        String formatted = "";
//        String[] unformatted = birthDate.split("/");
//        if (unformatted.length == 3) {
//            if ((unformatted[0].length() == 2) &&
//                    (unformatted[1].length() == 2) &&
//                    (unformatted[2].length() == 4)) {
//
//                formatted = unformatted[2] + "-" +
//                        unformatted[1] + "-" +
//                        unformatted[0];
//
//                success = true;
//            }
//        }
//
//        return (success ? formatted : "");
//    }
//
//    public static String formatDate(String date) {
//        @SuppressLint("SimpleDateFormat") SimpleDateFormat input = new SimpleDateFormat("yyyy-MM-dd");
//        @SuppressLint("SimpleDateFormat") SimpleDateFormat output = new SimpleDateFormat("dd-MM-yyyy");
//
//        Date datex;
//        String mDate = date;
//
//        try {
//            datex = input.parse(date);
//            assert datex != null;
//            mDate = output.format(datex);
//
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        return mDate;
//    }
//
//    public static String formatNameToCard(String name) {
//        String[] names = name.split(" ");
//        String finalName = name;
//
//        if (names.length > 0) {
//            for (int i = 0; i < names.length - 1; i++) {
//                if (i == 0) {
//                    finalName = names[i];
//                } else {
//                    String initial = " " + names[i].substring(0, 1);
//                    finalName = finalName.concat(initial);
//                }
//            }
//
//            finalName = finalName.concat(" " + names[names.length - 1]);
//        }
//
//        return finalName;
//    }
//
//    /**
//     * Returns the consumer friendly device name
//     */
//    public static String getDeviceName() {
//        String manufacturer = Build.MANUFACTURER;
//        String model = Build.MODEL;
//        if (model.startsWith(manufacturer)) {
//            return capitalize(model);
//        }
//        return capitalize(manufacturer) + " " + model;
//    }
//
//    private static String capitalize(String str) {
//        if (TextUtils.isEmpty(str)) {
//            return str;
//        }
//        char[] arr = str.toCharArray();
//        boolean capitalizeNext = true;
//
//        StringBuilder phrase = new StringBuilder();
//        for (char c : arr) {
//            if (capitalizeNext && Character.isLetter(c)) {
//
//                phrase.append(Character.toUpperCase(c));
//                capitalizeNext = false;
//                continue;
//            } else if (Character.isWhitespace(c)) {
//                capitalizeNext = true;
//            }
//
//            phrase.append(c);
//        }
//
//        return phrase.toString();
//    }
//
//    public static void ClearInformations(Context context) {
//        setUserLoggedIn(false);
//        MainActivity.instance.getUserInformation().ClearUserData(context);
//    }
//
//    public static @NonNull
//    Bitmap createBitmapFromView(@NonNull View view, int width, int height) {
//        if (width > 0 && height > 0) {
//            view.measure(View.MeasureSpec.makeMeasureSpec(
//                            convertDpToPixels(width), View.MeasureSpec.EXACTLY),
//                    View.MeasureSpec.makeMeasureSpec(convertDpToPixels(height), View.MeasureSpec.UNSPECIFIED));
//        }
//        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
//
//        Bitmap bitmap = Bitmap.createBitmap(view.getMeasuredWidth(),
//                view.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
//        Canvas canvas = new Canvas(bitmap);
//        Drawable background = view.getBackground();
//
//        if (background != null) {
//            background.draw(canvas);
//        }
//        view.draw(canvas);
//
//        return bitmap;
//    }
//
//    public static int convertDpToPixels(float dp) {
//        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
//                dp, Resources.getSystem().getDisplayMetrics()));
//    }
//
//    public static float convertPixelsToDp(float px, Context context){
//        Resources resources = context.getResources();
//        DisplayMetrics metrics = resources.getDisplayMetrics();
//        float dp = px / ((float)metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
//        return dp;
//    }
//
//    public static String getVolleyErrorDataString(byte[] data) {
//        return Base64.encodeToString(data, Base64.DEFAULT);
//    }
//
//    public static boolean isValidEmail(CharSequence target) {
//        DebugLog(TAG, "Testing this: " + target.toString() + " Valid? " + ((!TextUtils.isEmpty(target)) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches()));
//        return ((!TextUtils.isEmpty(target)) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches());
//    }
//
//    public static boolean isValidCPF(CharSequence target) {
//        String CPF = target.toString();
//        // considera-se erro CPF's formados por uma sequencia de numeros iguais
//        if (CPF.equals("00000000000") || CPF.equals("11111111111") ||
//                CPF.equals("22222222222") || CPF.equals("33333333333") ||
//                CPF.equals("44444444444") || CPF.equals("55555555555") ||
//                CPF.equals("66666666666") || CPF.equals("77777777777") ||
//                CPF.equals("88888888888") || CPF.equals("99999999999") ||
//                (CPF.length() != 11))
//            return (false);
//
//        char dig10, dig11;
//        int sm, i, r, num, peso;
//
//        // "try" - protege o codigo para eventuais erros de conversao de tipo (int)
//        try {
//            // Calculo do 1o. Digito Verificador
//            sm = 0;
//            peso = 10;
//            for (i = 0; i < 9; i++) {
//                // converte o i-esimo caractere do CPF em um numero:
//                // por exemplo, transforma o caractere '0' no inteiro 0
//                // (48 eh a posicao de '0' na tabela ASCII)
//                num = (int) (CPF.charAt(i) - 48);
//                sm = sm + (num * peso);
//                peso = peso - 1;
//            }
//
//            r = 11 - (sm % 11);
//            if ((r == 10) || (r == 11)) {
//                dig10 = '0';
//            } else {
//                dig10 = (char) (r + 48); // converte no respectivo caractere numerico
//            }
//
//            // Calculo do 2o. Digito Verificador
//            sm = 0;
//            peso = 11;
//            for (i = 0; i < 10; i++) {
//                num = (int) (CPF.charAt(i) - 48);
//                sm = sm + (num * peso);
//                peso = peso - 1;
//            }
//
//            r = 11 - (sm % 11);
//            if ((r == 10) || (r == 11)) {
//                dig11 = '0';
//            } else {
//                dig11 = (char) (r + 48);
//            }
//
//            // Verifica se os digitos calculados conferem com os digitos informados.
//            return ((dig10 == CPF.charAt(9)) && (dig11 == CPF.charAt(10)));
//
//        } catch (InputMismatchException erro) {
//            return (false);
//        }
//    }
//
//    public static boolean checkGooglePlayServiceAvailability(Context context) {
//
//        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
//        int resultCode = apiAvailability.isGooglePlayServicesAvailable(context);
//        return resultCode == ConnectionResult.SUCCESS;
//    }
//
//    public static boolean isNetworkAvailable(Context context) {
//        if (context == null) {
//            return false;
//        }
//        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
//        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
//        return ((activeNetworkInfo != null) && (activeNetworkInfo.isConnected()));
//    }
//
//    public static int getNetworkStatus(ConnectivityManager connectivityManager) {
//        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
//
//        if (null != activeNetwork) {
//            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI)
//                return CONSTANTS.CONNECTION_TYPE_WIFI;
//            if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE)
//                return CONSTANTS.CONNECTION_TYPE_MOBILE;
//        }
//        return CONSTANTS.CONNECTION_TYPE_NONE;
//    }
//
//    public static int getHeight(Context context, String text, int textSize, int deviceWidth) {
//        TextView textView = new TextView(context);
//        textView.setText(text);
//        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
//        int widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(deviceWidth, View.MeasureSpec.AT_MOST);
//        int heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
//        textView.measure(widthMeasureSpec, heightMeasureSpec);
//        return textView.getMeasuredHeight();
//    }
//
//    public static int getBirthByDate(String date) {
//        try {
//            int birthDate = Integer.parseInt(date.replace("-", ""));
//            final Date calendarDate = Calendar.getInstance().getTime();
//            @SuppressLint("SimpleDateFormat") String full = new SimpleDateFormat("yyyyMMdd").format(calendarDate);
//
//            int birth = Integer.parseInt(full) - birthDate;
//
//            return birth / 10000;
//        } catch (Exception e) {
//            return -1;
//        }
//    }
//
//    @SuppressLint("UseCompatTextViewDrawableApis")
//    public static void changeDrawableColor(TextView view, AppCompatActivity activity, int id) {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            view.setCompoundDrawableTintList(ColorStateList.valueOf(ContextCompat.getColor(activity, id)));
//        } else {
//            Drawable drawable = view.getCompoundDrawables()[0];
//            drawable = DrawableCompat.wrap(drawable);
//            DrawableCompat.setTint(drawable, activity.getResources().getColor(id));
//        }
//    }
//
//    public static boolean isValidContext(final Context context) {
//        if (context == null) {
//            return false;
//        }
//        if (context instanceof Activity) {
//            final Activity activity = (Activity) context;
//            if (activity.isDestroyed() || activity.isFinishing()) {
//                return false;
//            }
//        }
//        return true;
//    }
//
//    public static void getImageAt(Context activity, String link, ImageView imageView) {
//        try {
//            if (isValidContext(activity)) {
//                if (link.contains(BASE_FILES)) {
//                    String name = getFileNameServer(link);
//
//                    if (CONSTANTS.filesPath.contains(link)) {
//                        try {
//                            @SuppressLint("UseCompatLoadingForDrawables")
//                            Drawable drawable = activity.getResources().getDrawable(activity.getResources()
//                                    .getIdentifier(name, "drawable", activity.getPackageName()));
//
//                            Glide.with(activity)
//                                    .load(drawable)
//                                    .into(imageView);
//                        } catch (Exception e) {
//                            Glide.with(activity)
//                                    .load(link)
//                                    .into(imageView);
//                        }
//                    } else {
//                        Glide.with(activity)
//                                .load(link)
//                                .into(imageView);
//                    }
//                } else {
//                    Glide.with(activity)
//                            .load(link)
//                            .into(imageView);
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    public static void getImageAt(Context activity, String link, ImageView imageView, RequestOptions options) {
//        try {
//            if (activity != null) {
//                if (link.contains(BASE_FILES)) {
//                    String name = getFileNameServer(link);
//
//                    if (CONSTANTS.filesPath.contains(link)) {
//                        try {
//                            @SuppressLint("UseCompatLoadingForDrawables")
//                            Drawable drawable = activity.getResources().getDrawable(activity.getResources()
//                                    .getIdentifier(name, "drawable", activity.getPackageName()));
//
//                            Glide.with(activity)
//                                    .load(drawable)
//                                    .apply(options)
//                                    .into(imageView);
//                        } catch (Exception e) {
//                            Glide.with(activity)
//                                    .load(link)
//                                    .apply(options)
//                                    .into(imageView);
//                        }
//                    } else {
//                        Glide.with(activity)
//                                .load(link)
//                                .apply(options)
//                                .into(imageView);
//                    }
//                } else {
//                    Glide.with(activity)
//                            .load(link)
//                            .apply(options)
//                            .into(imageView);
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    public static void getImageAt(Context activity, String link, CustomTarget<Drawable> customTarget) {
//        try {
//            if (activity != null) {
//                if (link.contains(BASE_FILES)) {
//                    String name = getFileNameServer(link);
//
//                    if (CONSTANTS.filesPath.contains(link)) {
//                        try {
//                            @SuppressLint("UseCompatLoadingForDrawables")
//                            Drawable drawable = activity.getResources().getDrawable(activity.getResources()
//                                    .getIdentifier(name, "drawable", activity.getPackageName()));
//
//                            Glide.with(activity)
//                                    .load(drawable)
//                                    .into(customTarget);
//                        } catch (Exception e) {
//                            Glide.with(activity)
//                                    .load(link)
//                                    .into(customTarget);
//                        }
//                    } else {
//                        Glide.with(activity)
//                                .load(link)
//                                .into(customTarget);
//                    }
//                } else {
//                    Glide.with(activity)
//                            .load(link)
//                            .into(customTarget);
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    public static String getFileNameServer(String link) {
//        String name = link.replace(BASE_FILES, "");
//        name = name.replace("/", "_");
//        name = name.replace(".jpg", "");
//        name = name.replace(".png", "");
//        name = name.replace(".jpeg", "");
//
//        return name;
//    }
//
///*    public static String getHtmlImage (String link){
//        String finalLink = "";
//
//        if(link != null){
//            finalLink = getRandomURL() + "Content/images/" + link;
//
//            if(!link.isEmpty()){
//                if(link.contains("http") || link.contains("https")){
//                    finalLink = link;
//                }
//            }
//        }
//
//        return finalLink;
//    }*/
//
//
//    public static String getHtmlContent(String link) {
//        String finalLink = "";
//
//        if (link != null) {
//            finalLink = getRandomURL() + "Content/" + link;
//
//            if (!link.isEmpty()) {
//                if (link.contains("http") || link.contains("https")) {
//                    finalLink = link;
//                }
//            }
//        }
//
//        return finalLink;
//    }
//
//    public static Bitmap rotateBitmap(Bitmap bitmap, int orientation) {
//
//        Matrix matrix = new Matrix();
//        switch (orientation) {
//            case ExifInterface.ORIENTATION_FLIP_HORIZONTAL:
//                matrix.setScale(-1, 1);
//                break;
//            case ExifInterface.ORIENTATION_ROTATE_180:
//                matrix.setRotate(180);
//                break;
//            case ExifInterface.ORIENTATION_FLIP_VERTICAL:
//                matrix.setRotate(180);
//                matrix.postScale(-1, 1);
//                break;
//            case ExifInterface.ORIENTATION_TRANSPOSE:
//                matrix.setRotate(90);
//                matrix.postScale(-1, 1);
//                break;
//            case ExifInterface.ORIENTATION_ROTATE_90:
//                matrix.setRotate(90);
//                break;
//            case ExifInterface.ORIENTATION_TRANSVERSE:
//                matrix.setRotate(-90);
//                matrix.postScale(-1, 1);
//                break;
//            case ExifInterface.ORIENTATION_ROTATE_270:
//                matrix.setRotate(-90);
//                break;
//            default:
//                return bitmap;
//        }
//        try {
//            Bitmap bmRotated = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
//            bitmap.recycle();
//            return bmRotated;
//        } catch (OutOfMemoryError e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//    public static Bitmap flip(Bitmap src, int type) {
//        // criar new matrix para transformacao
//        Matrix matrix = new Matrix();
//        // if vertical
//        if (type == FLIP_VERTICAL) {
//            // y = y * -1
//            matrix.preScale(1.0f, -1.0f);
//        }
//        // if horizonal
//        else if (type == FLIP_HORIZONTAL) {
//            // x = x * -1
//            matrix.preScale(-1.0f, 1.0f);
//            // unknown type
//        } else {
//            return null;
//        }
//
//        // return image transformada
//        return Bitmap.createBitmap(src, 0, 0, src.getWidth(), src.getHeight(), matrix, true);
//    }
//
//    public static String getDatePost(String date) {
//        date = formatDateFromDB(date);
//        try {
//            String[] dateParts = date.split("/");
//            String finalValue;
//            finalValue = dateParts[0] + " " + getActualMouth(dateParts[1]) + " de " + dateParts[2];
//            return finalValue;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return "";
//        }
//    }
//
//    public static String getDateSocioPost(String date) {
//        date = formatDateFromDB(date);
//        try {
//            String[] dateParts = date.split("/");
//            String finalValue;
//            finalValue = getActualMouth(dateParts[1]).substring(0, 3) + " " + dateParts[0] + ", " + dateParts[2];
//            return finalValue;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return "";
//        }
//    }
//
//    public static String getActualMouth(String mouth) {
//        switch (mouth) {
//            case "01":
//                return "Janeiro";
//            case "02":
//                return "Fevereiro";
//            case "03":
//                return "Março";
//            case "04":
//                return "Abril";
//            case "05":
//                return "Maio";
//            case "06":
//                return "Junho";
//            case "07":
//                return "Julho";
//            case "08":
//                return "Agosto";
//            case "09":
//                return "Setembro";
//            case "10":
//                return "Outubro";
//            case "11":
//                return "Novembro";
//            case "12":
//                return "Dezembro";
//            default:
//                return "";
//        }
//    }
//
//
//    public static String getRandomURL() {
//        return CONSTANTS.serverContentImages;
//    }
//
//
//    public static String getPaymentType(String type) {
//        switch (type.toLowerCase()) {
//            case "subscription":
//                return "Assinatura";
//            case "upgrade":
//                return "Upgrade";
//            case "renew":
//                return "Renovação";
//            case "new-card":
//                return "Novo Cartão";
//
//            case "debt":
//                return "Cartão de Crédito";
//            case "transfer":
//                return "Transferência Bancária";
//            case "deposit":
//                return "Depósito Bancário";
//
//            case "payed":
//                return "Pago";
//            case "awaiting":
//                return "Aguardando Pagamento";
//            case "awaiting-confirm":
//                return "Aguardando Confirmação";
//            case "canceled":
//                return "Cancelado";
//
//            default:
//                return type;
//        }
//    }
//
//    public static String abbreviateScore(long count) {
//        if (count < 1000) return "" + count;
//        int exp = (int) (Math.log(count) / Math.log(1000));
//        return String.format("%.1f %c",
//                count / Math.pow(1000, exp),
//                "KMGTPE".charAt(exp-1)).replaceAll(" ", "").replaceAll(",", ".").toUpperCase(Locale.ROOT);
//    }
//
//    public static void setColorViews(String color, ArrayList<View> viewArrayList){
//        for (int i = 0; i < viewArrayList.size(); i++) {
//            viewArrayList.get(i).getBackground().setTint(Color.parseColor(color));
//        }
//    }
}

