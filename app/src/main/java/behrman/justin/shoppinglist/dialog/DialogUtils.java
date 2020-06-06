package behrman.justin.shoppinglist.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import behrman.justin.shoppinglist.R;

public class DialogUtils {

    public static void showConfirmDialog(Context context, int titleId, int promptId, final Runnable onAccept) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context, android.R.style.Theme_Material_Dialog_Alert);
        builder
                .setTitle(titleId)
                .setMessage(promptId);
        builder.setPositiveButton(R.string.confirm,new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                onAccept.run();
            }
        });
        builder.setNegativeButton(R.string.cancel, null);
        builder.create().show();
    }
}
