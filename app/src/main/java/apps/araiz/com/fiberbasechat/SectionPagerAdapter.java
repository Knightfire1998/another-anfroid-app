package apps.araiz.com.fiberbasechat;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

class SectionPagerAdapter extends FragmentPagerAdapter {
    public SectionPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        switch (position){

            case 0 :
                RequestFragment requestFragment = new RequestFragment();
                return requestFragment;

            case 1 :
                Chatsfragment chatsfragment = new Chatsfragment();
                return chatsfragment;

            case 2 :
                Friendsfragment friendsfragment = new Friendsfragment();
                return friendsfragment;

             default:
                 return null;
        }



    }

    @Override
    public int getCount() {
        return 3 ;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {

        switch (position){

            case 0:
                return "REQUESTS";

            case 1:
                return  "CHATS";

            case 2:
                return  "FRIENDS";

            default:
                return null;



        }


    }
}
