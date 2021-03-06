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


public class EMailFragment extends DialogFragment implements View.OnClickListener{
    EditText enterEMail;
    Button save, cancel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dialog_email, container, false);
        getDialog().setTitle(getResources().getString(R.string.enterComments));
        enterEMail = (EditText)view.findViewById(R.id.email_edittext);
        save = (Button)view.findViewById(R.id.save_email);
        cancel = (Button)view.findViewById(R.id.cancel_email);
        save.setOnClickListener(this);
        cancel.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.save_email:
                if (enterEMail.getText().toString().isEmpty())
                    Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.enterEMail), Toast.LENGTH_SHORT).show();
                else{
                    EMailTransmitter listener = (EMailTransmitter)getActivity();
                    listener.sendEmail(enterEMail.getText().toString());
                    enterEMail.setText("");
                    dismiss();
                }
                break;
            case R.id.cancel_email:
                enterEMail.setText("");
                dismiss();
                break;
        }

    }

    public interface EMailTransmitter{
        public void sendEmail(String email);
    }
}
