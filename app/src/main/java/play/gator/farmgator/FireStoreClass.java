package play.gator.farmgator;

import android.util.Log;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;
import java.util.List;

import play.gator.farmgator.BookOrder.BookOrder;
import play.gator.farmgator.BookOrder.FinalBookOrder;
import play.gator.farmgator.BookOrder.OrderModel;
import play.gator.farmgator.BookOrder.ProductAdapters.ProductTypeModel;
import play.gator.farmgator.Dashboard.Dashboard;
import play.gator.farmgator.Onboard.CropTypeModel;
import play.gator.farmgator.Onboard.FarmDetails;
import play.gator.farmgator.Onboard.farmer;
import play.gator.farmgator.SalesPerson.Registration;
import play.gator.farmgator.SalesPerson.user;
import play.gator.farmgator.ShowOrder.ShowOrder;

public class FireStoreClass {
    private  final FirebaseFirestore mFireStore = FirebaseFirestore.getInstance();
    private final String mCurrentUserID = FirebaseAuth.getInstance().getCurrentUser().getUid();
    public static final String FARM_DETAILS = "farm_details";
    public static final String SALES_PERSON_DETAILS = "sales_person_details";


    public void registerSalesPerson(Registration registration, user user){
        mFireStore.collection(SALES_PERSON_DETAILS).document(mCurrentUserID)
                .set(user, SetOptions.merge())
                .addOnSuccessListener(v->{
                    registration.registerSalesPersonSuccess();

                }).addOnFailureListener(v->{
            Toast.makeText(registration,"Something went wrong !",Toast.LENGTH_SHORT).show();
            Log.e("Registration :", v.getMessage());
        });
    }
    public void getSalesManDetails(FarmDetails farmDetails){
        mFireStore.collection(SALES_PERSON_DETAILS)
                .document(mCurrentUserID)
                .get()
                .addOnSuccessListener(v->{
                    user user = v.toObject(user.class);
                    if (user != null)
                        farmDetails.getUpdatedUserDetails(user);
                }).addOnFailureListener(v->{
            Toast.makeText(farmDetails,"Something went wrong !",Toast.LENGTH_SHORT).show();
            Log.e("UserDataGET :", v.getMessage());
        });
    }
    public void getSalesManDetails(FinalBookOrder farmDetails){
        mFireStore.collection(SALES_PERSON_DETAILS)
                .document(mCurrentUserID)
                .get()
                .addOnSuccessListener(v->{
                    user user = v.toObject(user.class);
                    if (user != null)
                        farmDetails.getUpdatedUserDetails(user);
                }).addOnFailureListener(v->{
            Toast.makeText(farmDetails,"Something went wrong !",Toast.LENGTH_SHORT).show();
            Log.e("UserDataGET :", v.getMessage());
        });
    }
    public void getSalesManDetails(Dashboard dashboard){
        mFireStore.collection(SALES_PERSON_DETAILS).document(mCurrentUserID)
                .get()
                .addOnSuccessListener(v->{
                    user user = v.toObject(play.gator.farmgator.SalesPerson.user.class);
                    if(user != null)
                        dashboard.getUserDetails(user);
                }).addOnFailureListener(v->{
            Toast.makeText(dashboard,"Something went wrong !",Toast.LENGTH_SHORT).show();
            Log.e("UserDataGET :", v.getMessage());
        });
    }
    public void getSalesManDetails(ShowOrder dashboard){
        mFireStore.collection(SALES_PERSON_DETAILS).document(mCurrentUserID)
                .get()
                .addOnSuccessListener(v->{
                    user user = v.toObject(play.gator.farmgator.SalesPerson.user.class);
                    if(user != null)
                        dashboard.getUserDetails(user);
                }).addOnFailureListener(v->{
            Toast.makeText(dashboard,"Something went wrong !",Toast.LENGTH_SHORT).show();
            Log.e("UserDataGET :", v.getMessage());
        });
    }
    public void addFarmDetailToFirebase(FarmDetails farmDetails, user user){
        mFireStore.collection(SALES_PERSON_DETAILS).document(mCurrentUserID)
                .set(user)
                .addOnSuccessListener(v->{
                    farmDetails.farmDetailsAddedSuccess();
                }).addOnFailureListener(v->{
            Toast.makeText(farmDetails,"Something went wrong !",Toast.LENGTH_SHORT).show();
            Log.e("FarmDetailsPost :", v.getMessage());
        });
    }
    public void getFarmDetails(BookOrder bookOrder){
        List<farmer> farmerList = new ArrayList<>();
        mFireStore.collection(FARM_DETAILS)
                .get()
                .addOnSuccessListener(v->{
                    Log.e("ListSize :",String.valueOf(v.getDocuments().size()));
                    for(DocumentSnapshot f : v.getDocuments()) {
                        farmer farmer = f.toObject(farmer.class);
                        assert farmer != null;
                        farmer.setFarmerid(f.getId());
                        farmerList.add(farmer);
                    }
                    bookOrder.getFarmerDetailsList(farmerList);
                    Toast.makeText(bookOrder,"Retrieved Success !",Toast.LENGTH_SHORT).show();
                    Log.e("farmerList",String.valueOf(farmerList.size()));
                }).addOnFailureListener(v->{
                Toast.makeText(bookOrder,"Something went wrong !",Toast.LENGTH_SHORT).show();
                Log.e("bookOrderGet :", v.getMessage());
        });
    }
    public void getFarmDetailsV2(BookOrder bookOrder){
        List<farmer> farmerList = new ArrayList<>();
        mFireStore.collection(SALES_PERSON_DETAILS)
                .document(mCurrentUserID)
                .get()
                .addOnSuccessListener(v->{
                    user user = v.toObject(user.class);
                    if(user  != null){
                        Log.e("ListSizeV2 :",String.valueOf(user.getFarmerList().size()));
                        farmerList.addAll(user.getFarmerList());
                    }
                    bookOrder.getFarmerDetailsList(farmerList);
                    Toast.makeText(bookOrder,"Retrieved Success !",Toast.LENGTH_SHORT).show();
                    Log.e("farmerList",String.valueOf(farmerList.size()));
                }).addOnFailureListener(v->{
            Toast.makeText(bookOrder,"Something went wrong !",Toast.LENGTH_SHORT).show();
            Log.e("bookOrderGet :", v.getMessage());
        });
    }
    public void getCropList(FarmDetails farmDetails){
        mFireStore.collection("crop_types")
                .document("oD3J22fRHDV8BvCgqZmF")
                .get()
                .addOnSuccessListener(v->{
                    CropTypeModel cropTypeModel = v.toObject(CropTypeModel.class);
                    if(cropTypeModel != null)
                       farmDetails.getCropList(cropTypeModel);
                    Log.e("GetCropList :", String.valueOf(cropTypeModel));
                }).addOnFailureListener(v->{
            Toast.makeText(farmDetails,"Something went wrong !",Toast.LENGTH_SHORT).show();
            Log.e("GetCropList :", v.getMessage());
        });
    }
    public void getProductList(FinalBookOrder finalBookOrder){
        mFireStore.collection("producttype")
                .document("yDV4h10s9cX8qLyW8oiI")
                .get()
                .addOnSuccessListener(v->{
                    ProductTypeModel productTypeModel = v.toObject(ProductTypeModel.class);
                    if(productTypeModel != null)
                        finalBookOrder.getProductList(productTypeModel);
                    Log.e("GetProductList :", String.valueOf(productTypeModel));
                }).addOnFailureListener(v->{
            Toast.makeText(finalBookOrder,"Something went wrong !",Toast.LENGTH_SHORT).show();
            Log.e("GetCropList :", v.getMessage());
        });
    }
    public void putOrderDetailsToFirebase(FinalBookOrder finalBookOrder, OrderModel orderModel,String farmerID,String farmID){
        mFireStore.collection("order_details")
                .document(mCurrentUserID)
                .collection("farmers")
                .document(farmerID)
                .collection("farm_list")
                .document(farmID)
                .set(orderModel,SetOptions.merge())
                .addOnSuccessListener(v->{
                    finalBookOrder.successfullyPlaced();
                })
                .addOnFailureListener(v->{
                    Toast.makeText(finalBookOrder,"Something went wrong !",Toast.LENGTH_SHORT).show();
                    Log.e("PutOrderDetails :", v.getMessage());
                });
    }
    public void putFarmDetailsToFirebase(FinalBookOrder finalBookOrder, farmer.FarmData farmDataList ,String farmerID){
        mFireStore.collection("order_details")
                .document(mCurrentUserID)
                .collection("farmers")
                .document(farmerID)
                .set(farmDataList,SetOptions.merge())
                .addOnSuccessListener(v->{
//                    finalBookOrder.successfullyPlaced();
                })
                .addOnFailureListener(v->{
                    Toast.makeText(finalBookOrder,"Something went wrong !",Toast.LENGTH_SHORT).show();
                    Log.e("PutOrderDetails :", v.getMessage());
                });
    }
    public void putSalesManDetailsToFirebase(FinalBookOrder finalBookOrder, user updatedUserDetails){
        mFireStore.collection("order_details")
                .document(mCurrentUserID)
                .set(updatedUserDetails,SetOptions.merge())
                .addOnSuccessListener(v->{
                    finalBookOrder.successfullyAddedUser();
                })
                .addOnFailureListener(v->{
                    Toast.makeText(finalBookOrder,"Something went wrong !",Toast.LENGTH_SHORT).show();
                    Log.e("PutOrderDetails :", v.getMessage());
                });
    }
    public void getOrderDetailsFromFirebase(FinalBookOrder finalBookOrder){
        mFireStore.collection("order_details")
                .document(mCurrentUserID)
                .get()
                .addOnSuccessListener(v->{
                    OrderModel orderModel1 = v.toObject(OrderModel.class);
                    if(orderModel1 != null){
                        finalBookOrder.getOrderDetails(orderModel1);
                    }
                })
                .addOnFailureListener(v->{
                    Toast.makeText(finalBookOrder,"Something went wrong !",Toast.LENGTH_SHORT).show();
                    Log.e("GetORderDetails :", v.getMessage());
                });
    }
    public void getAllOrderDetailsFromFirebase(ShowOrder showOrder, String farmerId, String farmID){
        mFireStore.collection("order_details")
                .document(mCurrentUserID)
                .collection("farmers")
                .document(farmerId)
                .collection("farm_list")
                .document(farmID)
                .get()
                .addOnSuccessListener(v->{
                    OrderModel orderModel = v.toObject(OrderModel.class);
                    if(orderModel != null){
                        showOrder.getOrderDetailsV2(orderModel);
                    }
                })
                .addOnFailureListener(v->{
                    Toast.makeText(showOrder,"Something went wrong !",Toast.LENGTH_SHORT).show();
                    Log.e("GetORderDetails :", v.getMessage());
                });
    }
}
