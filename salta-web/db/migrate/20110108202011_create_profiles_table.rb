class CreateProfilesTable < ActiveRecord::Migration
  def self.up
    create_table :profiles do |t|
      t.references :user
      t.string :first_name, :null => false
      t.string :last_name, :null => false
      t.string :email
      t.boolean :primary
      t.string :phone
      t.string :website
      t.string :facebook
      t.string :yahoo
      t.string :google
      t.string :twitter
      t.string :photo
      t.timestamps
    end
  end

  def self.down
    drop_table :profiles
  end
end
