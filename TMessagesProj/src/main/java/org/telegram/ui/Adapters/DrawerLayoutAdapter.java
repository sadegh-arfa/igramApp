/*
 * This is the source code of Telegram for Android v. 3.x.x.
 * It is licensed under GNU GPL v. 2 or later.
 * You should have received a copy of the license in this archive (see LICENSE).
 *
 * Copyright Nikolai Kudashov, 2013-2016.
 */

package org.telegram.ui.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import org.telegram.igram.R;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.ApplicationLoader;
import org.telegram.messenger.FileLog;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.MessagesController;
import org.telegram.messenger.UserConfig;
import org.telegram.ui.Cells.DividerCell;
import org.telegram.ui.Cells.DrawerActionCell;
import org.telegram.ui.Cells.DrawerProfileCell;
import org.telegram.ui.Cells.EmptyCell;
import org.telegram.ui.Cells.TextInfoCell;

import java.util.Locale;

public class DrawerLayoutAdapter extends BaseAdapter {

    private Context mContext;
    private int versionType = 4;
    private int contactsRow = 6;
    private int onlinecontactsRow = 7;
    private int contactChangesRow = 8;
    private int specificcontactRow = 9;
    private int themingRow = 11;
    private int themeStoreRow = 12;
    private int settingsRow = 13;
    private int plusSettingsRow = 14;
    private int channelRow = 16;
    private int Allmedia = 17;
    private int versionRow = 18;
    private int faqRow = 19;

    //private int rowCount = 0;

    public DrawerLayoutAdapter(Context context) {
        mContext = context;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public boolean isEnabled(int i) {
        return !(i == 0 || i == 1 || i == 5 || i == 10 || i == 15);
    }

    @Override
    public int getCount() {
        //return UserConfig.isClientActivated() ? 10 : 0;
        return UserConfig.isClientActivated() ? versionRow + 1 : 0;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @SuppressLint("StringFormatInvalid")
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        int type = getItemViewType(i);
        SharedPreferences themePrefs = ApplicationLoader.applicationContext.getSharedPreferences(AndroidUtilities.THEME_PREFS, AndroidUtilities.THEME_PREFS_MODE);
        if (type == 0) {
            if (view == null) {
                view = new DrawerProfileCell(mContext);
            }
            ((DrawerProfileCell) view).setUser(MessagesController.getInstance().getUser(UserConfig.getClientUserId()));
            ((DrawerProfileCell) view).refreshAvatar(themePrefs.getInt("drawerAvatarSize", 64), themePrefs.getInt("drawerAvatarRadius", 32));
        } else if (type == 1) {
            if (view == null) {
                view = new EmptyCell(mContext, AndroidUtilities.dp(8));
            }
            updateViewColor(view);
        } else if (type == 2) {
            if (view == null) {
                view = new DividerCell(mContext);
                view.setTag("drawerListDividerColor");
            }
            updateViewColor(view);
        } else if (type == 3) {
            if (view == null) {
                view = new DrawerActionCell(mContext);
            }
            updateViewColor(view);
            DrawerActionCell actionCell = (DrawerActionCell) view;
            //actionCell.setTextColor(themePrefs.getInt("drawerOptionColor", 0xff444444));
            //actionCell.setTextSize(themePrefs.getInt("drawerOptionSize", 15));
            //actionCell.setIconColor(themePrefs.getInt("drawerIconColor", 0xff737373));
            int color = themePrefs.getInt("drawerIconColor", 0xff737373);
            if (i == 2) {
                Drawable newGroup = mContext.getResources().getDrawable(R.drawable.menu_newgroup);
                newGroup.setColorFilter(color, PorterDuff.Mode.SRC_IN);
                actionCell.setTextAndIcon(LocaleController.getString("NewGroup", R.string.NewGroup), newGroup);
                //actionCell.setTextAndIcon(LocaleController.getString("NewGroup", R.string.NewGroup), R.drawable.menu_newgroup);
            } else if (i == 3) {
                Drawable secret = mContext.getResources().getDrawable(R.drawable.menu_secret);
                secret.setColorFilter(color, PorterDuff.Mode.SRC_IN);
                actionCell.setTextAndIcon(LocaleController.getString("NewSecretChat", R.string.NewSecretChat), secret);
            } else if (i == 4) {
                //actionCell.setTextAndIcon(LocaleController.getString("NewChannel", R.string.NewChannel), R.drawable.menu_broadcast);
                Drawable broadcast = mContext.getResources().getDrawable(R.drawable.menu_broadcast);
                broadcast.setColorFilter(color, PorterDuff.Mode.SRC_IN);
                actionCell.setTextAndIcon(LocaleController.getString("NewChannel", R.string.NewChannel), broadcast);
            } else if (i == contactsRow) {
                Drawable contacts = mContext.getResources().getDrawable(R.drawable.menu_contacts);
                contacts.setColorFilter(color, PorterDuff.Mode.SRC_IN);
                actionCell.setTextAndIcon(LocaleController.getString("Contacts", R.string.Contacts), contacts);
            } else if (i == onlinecontactsRow) {
                actionCell.setTextAndIcon(LocaleController.getString("OnlineCo0ntacts", R.string.OnlineContacts), R.drawable.menu_online_users);
            } else if (i == themingRow) {
                actionCell.setTextAndIcon(LocaleController.getString("Theming", R.string.Theming), R.drawable.menu_theming);
            } else if (i == themeStoreRow) {
                actionCell.setTextAndIcon(LocaleController.getString("ThemeStore", R.string.ThemeStore), R.drawable.menu_themes);
            } else if (i == plusSettingsRow) {
                actionCell.setTextAndIcon(LocaleController.getString("Theming", R.string.PlusSettings), R.drawable.menu_plus);
            } else if (i == settingsRow) {
                actionCell.setTextAndIcon(LocaleController.getString("Settings", R.string.Settings), R.drawable.menu_settings);
            } else if (i == channelRow) {
                actionCell.setTextAndIcon(LocaleController.getString("OfficialChannel", R.string.OfficialChannel), R.drawable.menu_broadcast);
            } else if (i == Allmedia) {
                actionCell.setTextAndIcon(LocaleController.getString("File Manager", R.string.FileManager), R.drawable.menu_file_manager);
            } else if (i == contactChangesRow) {
                Drawable contactChanges = mContext.getResources().getDrawable(R.drawable.menu_contacts_changes);
                contactChanges.setColorFilter(color, PorterDuff.Mode.SRC_IN);
                actionCell.setTextAndIcon(LocaleController.getString("contactChanges", R.string.contactChanges), contactChanges);
            } else if (i == specificcontactRow) {
                Drawable contactChanges = mContext.getResources().getDrawable(R.drawable.menu_specific_contacts);
                contactChanges.setColorFilter(color, PorterDuff.Mode.SRC_IN);
                actionCell.setTextAndIcon(LocaleController.getString("SpecificContacts", R.string.SpecificContacts), contactChanges);
            }
        } else if (type == versionType) {
            view = new TextInfoCell(mContext);
            updateViewColor(view);
            if (i == versionRow) {
                try {
                    PackageInfo pInfo = ApplicationLoader.applicationContext.getPackageManager().getPackageInfo(ApplicationLoader.applicationContext.getPackageName(), 0);
                    int code = pInfo.versionCode / 10;
                    String abi = "";
                    try {
                        if (Build.CPU_ABI != null) {
                            abi = Build.CPU_ABI;
                        } else {
                            switch (pInfo.versionCode % 10) {
                                case 0:
                                    abi = "arm";
                                    break;
                                case 1:
                                    abi = "arm-v7a";
                                    break;
                                case 2:
                                    abi = "x86";
                                    break;
                                case 3:
                                    abi = "universal";
                                    break;
                            }
                        }

                    } catch (Exception e) {

                        abi = "arm-v7a";

                    }

                    ((TextInfoCell) view).setText(String.format(Locale.US, LocaleController.getString("TelegramForAndroid", R.string.TelegramForAndroid) + "\nv%s (%d) %s", pInfo.versionName, code, abi));
                    ((TextInfoCell) view).setTextColor(themePrefs.getInt("drawerVersionColor", 0xffa3a3a3));
                    ((TextInfoCell) view).setTextSize(themePrefs.getInt("drawerVersionSize", 13));
                } catch (Exception e) {
                    FileLog.e("tmessages", e);
                }
            }
        }
        return view;
    }

    private void updateViewColor(View v) {
        SharedPreferences themePrefs = ApplicationLoader.applicationContext.getSharedPreferences(AndroidUtilities.THEME_PREFS, AndroidUtilities.THEME_PREFS_MODE);
        int mainColor = themePrefs.getInt("drawerListColor", 0xffffffff);
        int value = themePrefs.getInt("drawerRowGradient", 0);
        boolean b = true;//themePrefs.getBoolean("drawerRowGradientListCheck", false);
        if (value > 0 && !b) {
            GradientDrawable.Orientation go;
            switch (value) {
                case 2:
                    go = GradientDrawable.Orientation.LEFT_RIGHT;
                    break;
                case 3:
                    go = GradientDrawable.Orientation.TL_BR;
                    break;
                case 4:
                    go = GradientDrawable.Orientation.BL_TR;
                    break;
                default:
                    go = GradientDrawable.Orientation.TOP_BOTTOM;
            }

            int gradColor = themePrefs.getInt("drawerRowGradientColor", 0xffffffff);
            int[] colors = new int[]{mainColor, gradColor};
            GradientDrawable gd = new GradientDrawable(go, colors);
            v.setBackgroundDrawable(gd);
        }
    }

    @Override
    public int getItemViewType(int i) {
        if (i == 0) {
            return 0;
        } else if (i == 1) {
            return 1;
        } else if (i == 5 || i == 10 || i == 15) {
            return 2;
        }
        //new
        else if (i == versionRow) {
            return versionType;
        }
        //
        return 3;
    }

    @Override
    public int getViewTypeCount() {
        return 5;
    }

    @Override
    public boolean isEmpty() {
        return !UserConfig.isClientActivated();
    }
}
