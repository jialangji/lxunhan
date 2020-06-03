package com.ay.lxunhan.widget.azlist;




import com.ay.lxunhan.bean.FriendBean;

import java.util.Comparator;

public class LettersComparator implements Comparator<AZItemEntity<FriendBean>> {

	public int compare(AZItemEntity<FriendBean> o1, AZItemEntity<FriendBean> o2) {
		if (o1.getSortLetters().equals("@")
			|| o2.getSortLetters().equals("#")) {
			return 1;
		} else if (o1.getSortLetters().equals("#")
				   || o2.getSortLetters().equals("@")) {
			return -1;
		} else {
			return o1.getSortLetters().compareTo(o2.getSortLetters());
		}
	}

}
