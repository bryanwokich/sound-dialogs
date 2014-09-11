package com.soundsoftware.plugins;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.PluginResult;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.widget.EditText;

public class SoundDialogs extends CordovaPlugin {

	private static final String PASSWORD_PROMPT = "passwordPrompt";

	@Override
	public boolean execute(String action, JSONArray args,
			final CallbackContext callbackContext) throws JSONException {

		if (PASSWORD_PROMPT.equals(action)) {
			this.passwordPrompt(args.getString(0), args.getString(1), args.getJSONArray(2), callbackContext);
			return true;
		} else {
			callbackContext
					.error("sound.dialogs." + action + " does not exist");
			return false;
		}
	}

    /**
     * Builds and shows a native Android prompt dialog with given title, message, and buttons.
     * This input is obfuscated as is expected of passwords.
     *
     * The following results are returned to the JavaScript callback identified by callbackId:
     *     buttonIndex			Index number of the button selected
     *     password				The text entered in the prompt dialog box
     *
     * @param message           The message the dialog should display
     * @param title             The title of the dialog
     * @param buttonLabels      A comma separated list of button labels.  This supports a positive and a negative button only.  Negative comes first.
     * @param callbackContext   The callback context.
     */
	public synchronized void passwordPrompt(final String message, final String title, final JSONArray buttonLabels, final CallbackContext callbackContext){
		Runnable prompt = new Runnable() {

			@Override
			public void run() {

				final JSONObject result = new JSONObject();
				AlertDialog.Builder alert = new AlertDialog.Builder(
						cordova.getActivity(), AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
				alert.setTitle(title);
				alert.setMessage(message);
				alert.setCancelable(true);
				final EditText promptInput = new EditText(
						cordova.getActivity());
				promptInput.setInputType(InputType.TYPE_CLASS_TEXT
						| InputType.TYPE_TEXT_VARIATION_PASSWORD);
				promptInput
						.setTransformationMethod(PasswordTransformationMethod
								.getInstance());
				alert.setView(promptInput);
				try {
					alert.setPositiveButton(buttonLabels.getString(1),
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int whichButton) {
									try {
										result.put("password", promptInput
												.getText().toString().trim());
										result.put("submit", 1);
									} catch (JSONException e) {
										e.printStackTrace();
									}
									callbackContext
											.sendPluginResult(new PluginResult(
													PluginResult.Status.OK,
													result));

								}
							});
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				try {
					alert.setNegativeButton(buttonLabels.getString(0),
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int whichButton) {
									try {
										result.put("submit", 0);
									} catch (JSONException e) {
										e.printStackTrace();
									}
									callbackContext.sendPluginResult(new PluginResult(PluginResult.Status.OK, result));
								}
							});
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				alert.show();
			}
		};

		cordova.getActivity().runOnUiThread(prompt);
	}
}
