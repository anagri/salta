require File.expand_path(File.dirname(__FILE__) + '/../spec_helper')

describe UserSessionsController do
  before(:each) do
    @password = 'abcd1234'
    @user = Factory.create(:user, :password => @password)
  end
  
  it "should login using json request" do
    post :create, :format => 'json', :user_session => {:email => @user.email, :password => @password}
    response.should be_success
    response.body.should be_blank
  end
end