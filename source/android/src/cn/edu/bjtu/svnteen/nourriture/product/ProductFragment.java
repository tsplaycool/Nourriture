package cn.edu.bjtu.svnteen.nourriture.product;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;
import cn.edu.bjtu.svnteen.nourriture.R;
import cn.edu.bjtu.svnteen.nourriture.adapter.ProductsListViewAdapter;
import cn.edu.bjtu.svnteen.nourriture.bean.Product;
import cn.edu.bjtu.svnteen.nourriture.core.MessageID;
import cn.edu.bjtu.svnteen.nourriture.core.MessageManager;
import cn.edu.bjtu.svnteen.nourriture.observer.IProductJsonObserver;
import cn.edu.bjtu.svnteen.nourriture.utils.JumperUtils;
import cn.edu.bjtu.svnteen.nourriture.utils.ProductUtils;

public class ProductFragment extends Fragment implements IProductJsonObserver {

	private Context mContext;
	private View mRootView;
	private ListView mListView;
	private View mLoadingView;
	private ProductsListViewAdapter mListViewAdapter;
	private ArrayList<Product> mArrayListProduct;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		MessageManager.getInstance().attachMessage(
				MessageID.OBSERVER_PRODUCT_JSON, this);// 在onAttach中注册消息
	}

	@Override
	public void onDetach() {
		super.onDetach();
		MessageManager.getInstance().detachMessage(
				MessageID.OBSERVER_PRODUCT_JSON, this);// 在onDetach中解除消息
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mContext = getActivity();
		mRootView = inflater.inflate(R.layout.product, null);
		mListView = (ListView) mRootView.findViewById(R.id.product_listview);
		mLoadingView = mRootView.findViewById(R.id.product_progressbar);
		ProductUtils.getAllProducts();
		return mRootView;
	}

	@Override
	public void IProductJsonObserver_All(ArrayList<Product> products) {
		mArrayListProduct = products;
		mListViewAdapter = new ProductsListViewAdapter(mContext,
				mArrayListProduct);
		mListView.setAdapter(mListViewAdapter);
		mListViewAdapter.notifyDataSetChanged();
		mLoadingView.setVisibility(View.GONE);
		mListView.setVisibility(View.VISIBLE);
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				JumperUtils.JumpToProductDetails(mArrayListProduct
						.get(position));
			}
		});
	}

	@Override
	public void IProductJsonObserver_TagAndIngredient(String json) {

	}

	@Override
	public void IProductJsonObserver_Failed() {
		Toast.makeText(mContext, "加载失败", Toast.LENGTH_SHORT).show();
	}

	@Override
	public void IProductJsonObserver_ID(Product product) {

	}

	@Override
	public void IProductJsonObserver_Detail(Product product) {
		// TODO Auto-generated method stub

	}

	@Override
	public void IProductJsonObserver_Detail_ingredients(Product product) {
		// TODO Auto-generated method stub

	}

	@Override
	public void IProductJsonObserver_Detail_nutrition(Product product) {
		// TODO Auto-generated method stub

	}

}
