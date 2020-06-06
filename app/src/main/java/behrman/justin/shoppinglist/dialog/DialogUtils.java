package behrman.justin.shoppinglist.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

public class DialogUtils {

    public static void showConfirmDialog(Context context, String title, String prompt, final Runnable onAccept) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context, android.R.style.Theme_Material_Dialog_Alert);
        builder
                .setTitle(title)
                .setMessage(prompt);
        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                onAccept.run();
            }
        });
        builder.setNegativeButton("Cancel", null);
        builder.create().show();
    }
}
