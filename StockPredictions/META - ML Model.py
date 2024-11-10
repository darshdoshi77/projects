from sklearn.linear_model import LinearRegression
from sklearn.metrics import mean_squared_error
import matplotlib.pyplot as plt
import pandas as pd
from sklearn.preprocessing import StandardScaler
import yfinance as yf

ticker_symbol = 'META'
stock_data = yf.download(ticker_symbol, start='2020-01-01', end='2024-01-01', interval='1d')
stock_data.dropna(inplace=True)

stock_data['20d_MA'] = stock_data['Close'].rolling(window=20).mean()
stock_data['Previous_Close'] = stock_data['Close'].shift(2)    
stock_data['Previous_Open'] = stock_data['Open'].shift(2)     
stock_data['Previous_Volume'] = stock_data['Volume'].shift(2)  


delta = stock_data['Close'].diff()
gain = delta.where(delta > 0, 0)
loss = -delta.where(delta < 0, 0)
avg_gain = gain.rolling(window=14).mean()
avg_loss = loss.rolling(window=14).mean()
rs = avg_gain / avg_loss
stock_data['RSI'] = 100 - (100 / (1 + rs))
stock_data['Daily Return'] = ((stock_data['Close'] - stock_data['Previous_Close']) / stock_data['Previous_Close']) * 100

stock_data["Spread"] = stock_data["High"] - stock_data["Low"]
stock_data['Target_Close'] = stock_data['Close'].shift(-1)  

feature_columns = ['Previous_Close', '20d_MA', 'Volume', 'RSI', 'Previous_Volume', 'Daily Return', 'Spread', 'Previous_Open']
stock_data = stock_data.dropna(subset=feature_columns + ['Target_Close'])

split_index = int(len(stock_data) * 0.8)
train_data = stock_data.iloc[:split_index]
test_data = stock_data.iloc[split_index:]

X_train = train_data[feature_columns]
y_train = train_data['Target_Close']
X_test = test_data[feature_columns]
y_test = test_data['Target_Close']

scaler = StandardScaler()
X_train_scaled = scaler.fit_transform(X_train)
X_test_scaled = scaler.transform(X_test)

model = LinearRegression()
model.fit(X_train_scaled, y_train)

y_pred = model.predict(X_test_scaled)

rmse = mean_squared_error(y_test, y_pred, squared=False)
print("Root Mean Square Error (RMSE):", rmse)

coefficients = pd.DataFrame(model.coef_, feature_columns, columns=['Coefficient'])
print(coefficients)

y_pred_series = pd.Series(y_pred, index=y_test.index)

plt.figure(figsize=(12, 6))
plt.plot(y_test.index, y_test, label="Actual Next Day Closing Price", color="blue")
plt.plot(y_pred_series.index, y_pred_series, label="Predicted Next Day Closing Price", color="red")
plt.xlabel("Date")
plt.ylabel("Closing Price")
plt.title("Actual vs Predicted Next Day Closing Price")
plt.legend()
plt.show()
