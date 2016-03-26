package ua.ukraine.ukrroad.ukrroad.dialogfragment;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import ua.ukraine.ukrroad.ukrroad.R;

public class ProblemFragment extends DialogFragment implements AdapterView.OnItemClickListener {
    ListView listView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().setTitle(getResources().getString(R.string.chooseAction));
        View view = inflater.inflate(R.layout.fragment_problem, container, false);
        listView = (ListView) view.findViewById(R.id.fragmentProblemListView);
        String[] listDefects = getActivity().getResources().getStringArray(R.array.defects);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, listDefects);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);

        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ProblemTransmitter problemTransmitter = (ProblemTransmitter) getActivity();
        problemTransmitter.sendProblemName(listView.getItemAtPosition(position).toString().trim());
        dismiss();
    }

    public interface ProblemTransmitter {
        void sendProblemName(String problem);
    }

}
