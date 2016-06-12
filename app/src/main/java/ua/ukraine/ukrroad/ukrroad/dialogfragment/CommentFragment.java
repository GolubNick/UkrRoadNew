package ua.ukraine.ukrroad.ukrroad.dialogfragment;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import ua.ukraine.ukrroad.ukrroad.R;

public class CommentFragment extends DialogFragment implements View.OnClickListener {
    EditText enterComments;
    Button save, cancel;
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_dialog_comment, container, false);
        getDialog().setTitle(getResources().getString(R.string.enterComments));
        enterComments = (EditText) view.findViewById(R.id.comment_edittext);
        save = (Button) view.findViewById(R.id.save_comment);
        cancel = (Button) view.findViewById(R.id.cancel_comment);
        save.setOnClickListener(this);
        cancel.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.save_comment:
                if (enterComments.getText().toString().isEmpty())
                    Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.enterComments), Toast.LENGTH_SHORT).show();
                else {
                    CommentTransmitter listener = (CommentTransmitter) getActivity();
                    listener.sendComments(enterComments.getText().toString());
                    enterComments.setText("");
                    dismiss();
                }
                break;
            case R.id.cancel_comment:
                enterComments.setText("");
                dismiss();
                break;
        }

    }

    public interface CommentTransmitter {
        public void sendComments(String comments);
    }
}
