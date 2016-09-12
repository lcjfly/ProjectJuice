package com.main.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.luchenjie.projectjuice.R;
import com.main.BinaryTextViewOnClickListener;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SerialTestFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SerialTestFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SerialTestFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public SerialTestFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SerialTestFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SerialTestFragment newInstance(String param1, String param2) {
        SerialTestFragment fragment = new SerialTestFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_serial_test, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        Button sendBtn = (Button) getView().findViewById(R.id.sendBtn);
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView sendLogsText = (TextView) getView().findViewById(R.id.sendLogsText);
                sendLogsText.append(getTime()+"-201010101010101010\n");
                scrollToBottom();
            }
        });

        final TextView byte16 = (TextView) getView().findViewById(R.id.byte16);
        byte16.setOnClickListener(new BinaryTextViewOnClickListener());

        final TextView byte15 = (TextView) getView().findViewById(R.id.byte15);
        byte15.setOnClickListener(new BinaryTextViewOnClickListener());
    }

    public void scrollToBottom() {
        Handler mHandler = new Handler();

        mHandler.post(new Runnable() {
            public void run() {
                ScrollView sendLogsSV = (ScrollView) getView().findViewById(R.id.sendLogs);
                sendLogsSV.fullScroll(ScrollView.FOCUS_DOWN);
                ScrollView receiveLogsSV = (ScrollView) getView().findViewById(R.id.receiveLogs);
                receiveLogsSV.fullScroll(ScrollView.FOCUS_DOWN);
            }
        });
    }

    private String getTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("hh:mm:ss");
        return formatter.format(new Date());
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

}
