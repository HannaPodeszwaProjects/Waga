import tensorflow as tf
from tensorflow.keras.optimizers import RMSprop
import keras_preprocessing
from keras_preprocessing import image
from keras.preprocessing.image import ImageDataGenerator
import matplotlib.pyplot as plt
import matplotlib.image as mpimg
import os
from keras.models import load_model


training_datagen = ImageDataGenerator(rescale = 1./255)
validation_datagen = ImageDataGenerator(rescale = 1./255)


train_dir = r"fruitSplit\train"
train_gen = training_datagen.flow_from_directory(train_dir, #directory containing various fruits
                                                target_size=(150, 150), # all images will be resized to 150, 150 when it is loaded
                                                class_mode="categorical") #categorial because of multi-class classification. 
                                                #if only two classes, then "binay"

val_dir = r"fruitSplit\val"
val_gen = validation_datagen.flow_from_directory(val_dir, #directory containing various fruits
                                                target_size=(150, 150), # all images will be resized to 150, 150 when it is loaded
                                                class_mode="categorical")



model = load_model("fruit.h5")


model.summary()


validation_accuracy = 0.98

class myCallback(tf.keras.callbacks.Callback):
    def on_epoch_end(self, epoch, logs={}):
        if(logs.get("val_accuracy") is not None and logs.get("val_accuracy") >= validation_accuracy):
            print("\nReached desired validation accuracy, so cancelling training")
            self.model.stop_training=True
            
callbacks = myCallback()


# In[15]:


model.compile(loss = "categorical_crossentropy", optimizer='rmsprop', metrics=['accuracy'])


# In[16]:


with tf.device('/gpu:0'):
    fruit_model = model.fit(train_gen, epochs=100, validation_data=val_gen, verbose=1, 
                                  callbacks = [callbacks], workers=10)

filepath = r"model"
tf.keras.models.save_model(
    model,
    filepath,
    overwrite=True,
    include_optimizer=True,
    save_format="tf",
    signatures=None
)

model.save("fruit.h5")




# In[17]:


# Convert the model.
converter = tf.compat.v1.lite.TFLiteConverter.from_keras_model_file('fruit.h5')
tflite_model = converter.convert()

# Save the model.
with open('model.tflite', 'wb') as f:
  f.write(tflite_model)

  

