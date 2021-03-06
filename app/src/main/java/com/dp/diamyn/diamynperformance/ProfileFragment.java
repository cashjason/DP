package com.dp.diamyn.diamynperformance;

import android.Manifest.permission;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.UUID;

import static android.app.Activity.RESULT_OK;

public class ProfileFragment extends Fragment implements View.OnClickListener {
    String ID;
    FirebaseUser user;
    FirebaseDatabase database;
    Button done, pic;
    ImageView myImage;
    EditText pName, pNum, pPhone, pHeight, pWeight;
    Spinner pTeam, pYear, pPosition;
    DatabaseReference mDatabase;
    FirebaseStorage storage;
    StorageReference storageReference;
    private static final int PICK_FROM_GALLERY = 1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        myImage = view.findViewById(R.id.pictureView);
        done = view.findViewById(R.id.doneBtn);
        done.setOnClickListener(this);
        pic = view.findViewById(R.id.picBtn);
        pic.setOnClickListener(this);
        pName = view.findViewById(R.id.profName);
        pNum = view.findViewById(R.id.profNum);
        pPhone = view.findViewById(R.id.profPhone);
        pHeight = view.findViewById(R.id.profHeight);
        pWeight = view.findViewById(R.id.profWeight);
        pTeam = view.findViewById(R.id.teamSpinner);
        pYear = view.findViewById(R.id.yearSpinner);
        pPosition = view.findViewById(R.id.positionSpinner);
        user = FirebaseAuth.getInstance().getCurrentUser();
        database = FirebaseDatabase.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        ID = user.getUid();
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        database.getReference("users/" + ID + "/PlayerInformation/Name").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                pName.setText(value);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        database.getReference("users/" + ID + "/PlayerInformation/Number").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                pNum.setText(value);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        database.getReference("users/" + ID + "/PlayerInformation/Phone").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                pPhone.setText(value);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        database.getReference("users/" + ID + "/PlayerInformation/Height").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                pHeight.setText(value);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        database.getReference("users/" + ID + "/PlayerInformation/Weight").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                pWeight.setText(value);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        database.getReference("users/" + ID + "/PlayerInformation/Team").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                String[] pTeamArr = getResources().getStringArray(R.array.team_arrays);
                pTeam.setSelection(0);
                for (int j = 0; j < pTeamArr.length; j++) {
                    try{
                        if (value.equals(pTeamArr[j])) {
                            pTeam.setSelection(j);
                        }
                    }catch(NullPointerException e){
                        continue;
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        database.getReference("users/" + ID + "/PlayerInformation/Year").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                String[] pYearArr = getResources().getStringArray(R.array.year_arrays);
                pYear.setSelection(0);
                for (int j = 0; j < pYearArr.length; j++) {
                    try{
                        if (value.equals(pYearArr[j])) {
                            pYear.setSelection(j);
                        }
                    }catch(NullPointerException e){
                        continue;
                    }

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        database.getReference("users/" + ID + "/PlayerInformation/Position").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                String[] pPosArr = getResources().getStringArray(R.array.position_arrays);
                pPosition.setSelection(0);
                for (int j = 0; j < pPosArr.length; j++) {
                    try{
                        if (value.equals(pPosArr[j])) {
                            pPosition.setSelection(j);
                        }
                    }catch(NullPointerException e){
                        continue;
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        File localFile = null;
        StorageReference ref = storageReference.child("images/"+ ID + "profilePicture");
        try {
            localFile = File.createTempFile("images", "jpg");

            File finalLocalFile = localFile;
            ref.getFile(localFile)
                    .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                            // Successfully downloaded data to local file
                            // ...
                            Bitmap bitmap = BitmapFactory.decodeFile(finalLocalFile.getAbsolutePath());
                            myImage.setImageBitmap(bitmap);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle failed download
                    // ...
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.doneBtn) {
            mDatabase.child("users").child(ID).child("PlayerInformation").child("Name").setValue(pName.getText().toString());
            mDatabase.child("users").child(ID).child("PlayerInformation").child("Number").setValue(pNum.getText().toString());
            mDatabase.child("users").child(ID).child("PlayerInformation").child("Phone").setValue(pPhone.getText().toString());
            mDatabase.child("users").child(ID).child("PlayerInformation").child("Team").setValue(pTeam.getSelectedItem().toString());
            mDatabase.child("users").child(ID).child("PlayerInformation").child("Year").setValue(pYear.getSelectedItem().toString());
            mDatabase.child("users").child(ID).child("PlayerInformation").child("Position").setValue(pPosition.getSelectedItem().toString());
            mDatabase.child("users").child(ID).child("PlayerInformation").child("Height").setValue(pHeight.getText().toString());
            mDatabase.child("users").child(ID).child("PlayerInformation").child("Weight").setValue(pWeight.getText().toString());
            Toast.makeText(getContext(), "Profile Updated", Toast.LENGTH_SHORT).show();
        }
        if (i == R.id.picBtn) {

            try {
                if (ActivityCompat.checkSelfPermission(super.getActivity(), android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(super.getActivity(), new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, PICK_FROM_GALLERY);
                } else {
                    Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(galleryIntent, PICK_FROM_GALLERY);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults)
    {
        switch (requestCode) {
            case PICK_FROM_GALLERY:
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(galleryIntent, PICK_FROM_GALLERY);
                } else {
                }
                break;
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_FROM_GALLERY && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };
            Cursor cursor = getContext().getContentResolver().query(selectedImage,filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();
            System.out.println("PICTURE PATH IS = " + picturePath);

            //compress picture

            Bitmap bitmap = BitmapFactory.decodeFile(picturePath);

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 10, byteArrayOutputStream);
            myImage.setImageBitmap(bitmap);
            byte[] byteData = byteArrayOutputStream.toByteArray();
            upImage(byteData);

//            Bitmap bitmap = BitmapFactory.decodeFile(picturePath);
//            myImage.setImageBitmap(bitmap);
//            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//            bitmap.compress(Bitmap.CompressFormat.JPEG, 20, byteArrayOutputStream);
//
//            uploadImage(selectedImage);
        }
    }

    private void upImage(byte[] byteData) {
        StorageReference ref = storageReference.child("images/"+ ID + "profilePicture");
        UploadTask uploadTask = ref.putBytes(byteData);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                Uri downloadUrl = taskSnapshot.getDownloadUrl();
            }
        });
    }


    private void uploadImage(Uri filePath) {

        if(filePath != null)
        {

            final ProgressDialog progressDialog = new ProgressDialog(getContext());
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            StorageReference ref = storageReference.child("images/"+ ID + "profilePicture");
            ref.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                            Toast.makeText(getContext(), "Uploaded", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(getContext(), "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot
                                    .getTotalByteCount());
                            progressDialog.setMessage("Uploaded "+(int)progress+"%");
                        }
                    });
        }
    }


}
